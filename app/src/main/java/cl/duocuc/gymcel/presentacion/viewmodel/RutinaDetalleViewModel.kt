package cl.duocuc.gymcel.presentacion.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duocuc.gymcel.data.FactoryProvider
import cl.duocuc.gymcel.data.local.entities.ItemRutinaEntity
import cl.duocuc.gymcel.data.local.entities.ItemTreinoEntity
import cl.duocuc.gymcel.data.local.entities.RutinaEntity
import cl.duocuc.gymcel.data.local.entities.TreinoEntity
import cl.duocuc.gymcel.domain.model.*
import cl.duocuc.gymcel.utils.CalculadoraPesos
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Instant

class RutinaDetalleViewModel : ViewModel() {

    private val rutinaRepository by lazy {
        FactoryProvider.repositoryFactory().create(RutinaEntity::class.java)
    }

    private val itemRutinaRepository by lazy {
        FactoryProvider.repositoryFactory().create(ItemRutinaEntity::class.java)
    }

    private val treinoRepository by lazy {
        FactoryProvider.repositoryFactory().create(TreinoEntity::class.java)
    }

    private val itemTreinoRepository by lazy {
        FactoryProvider.repositoryFactory().create(ItemTreinoEntity::class.java)
    }

    // ---------------- STATES ----------------
    private val _rutina = MutableStateFlow<Rutina?>(null)
    val rutina: StateFlow<Rutina?> = _rutina.asStateFlow()

    private val _seriesUI = MutableStateFlow<Map<Long, List<SerieUI>>>(emptyMap())
    val seriesUI: StateFlow<Map<Long, List<SerieUI>>> = _seriesUI.asStateFlow()

    private val _editable = MutableStateFlow(true)
    val editable: StateFlow<Boolean> = _editable.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    // Identificador del treino actual en uso
    private var treinoIdActual: Long? = null

    // ---------------- PUBLIC API ----------------

    fun cargarRutina(rutinaId: Long) {
        _loading.value = true

        viewModelScope.launch {
            try {
                val rutinaEntity = rutinaRepository.getById(rutinaId) ?: return@launch
                _rutina.value = rutinaEntity.toDomain()

                val detallesBase = cargarDetallesRutina(rutinaId)
                val treinoActivo = cargarOCrearTreino(rutinaId)

                _editable.value = treinoActivo.rutina_id == 0L

                val seriesMap = cargarSeriesUI(detallesBase, treinoActivo.id)
                _seriesUI.value = seriesMap

            } finally {
                _loading.value = false
            }
        }
    }

    fun actualizarCarga(detalleId: Long, serieIndex: Int, nuevaCarga: Double) {
        if (!_editable.value) return

        val copia = _seriesUI.value.toMutableMap()
        val series = copia[detalleId]?.toMutableList() ?: return

        val serie = series.getOrNull(serieIndex) ?: return
        series[serieIndex] = serie.copy(carga = nuevaCarga)

        copia[detalleId] = series
        _seriesUI.value = copia
    }

    fun actualizarReps(detalleId: Long, serieIndex: Int, nuevasReps: Int) {
        if (!_editable.value) return

        val copia = _seriesUI.value.toMutableMap()
        val series = copia[detalleId]?.toMutableList() ?: return

        val serie = series.getOrNull(serieIndex) ?: return
        series[serieIndex] = serie.copy(reps = nuevasReps)

        copia[detalleId] = series
        _seriesUI.value = copia
    }

    fun actualizarUnidad(detalleId: Long, serieIndex: Int, nuevaUnidad: String) {
        if (!_editable.value) return

        val copia = _seriesUI.value.toMutableMap()
        val series = copia[detalleId]?.toMutableList() ?: return

        val serie = series.getOrNull(serieIndex) ?: return

        val converted = CalculadoraPesos.convertirPeso(
            serie.carga,
            serie.unidad,
            nuevaUnidad
        )

        series[serieIndex] = serie.copy(
            unidad = nuevaUnidad,
            carga = converted
        )

        copia[detalleId] = series
        _seriesUI.value = copia
    }

    fun guardarTreino() {
        val treinoId = treinoIdActual ?: return

        viewModelScope.launch {
            val detallesBase = _seriesUI.value

            for ((detalleId, series) in detallesBase) {
                val detalleEntity = itemRutinaRepository.getById(detalleId) ?: continue
                series.forEach { s ->
                    itemTreinoRepository.save(
                        ItemTreinoEntity(
                            id = 0,
                            treino_id = treinoId,
                            exercise_externalid = detalleEntity.exercise_externalid,
                            effective_reps = s.reps,
                            effective_load = s.carga,
                            load_unit = s.unidad,
                            rir = null,
                            rest_nanos = 0L
                        )
                    )
                }
            }

            // Asociar treino a la rutina
            val rutinaId = _rutina.value?.id ?: return@launch
            val treino = treinoRepository.getById(treinoId) ?: return@launch

            treinoRepository.update(
                treino.copy(rutina_id = rutinaId)
            )

            _editable.value = false
        }
    }

    // ---------------- INTERNALS ----------------

    private suspend fun cargarDetallesRutina(rutinaId: Long): List<ItemRutinaEntity> {
        return itemRutinaRepository.getAll().filter { it.rutina_id == rutinaId }
    }

    private suspend fun cargarOCrearTreino(rutinaId: Long): TreinoEntity {
        val treinoExistente = treinoRepository.getAll()
            .find { it.rutina_id == rutinaId }

        return if (treinoExistente != null) {
            treinoIdActual = treinoExistente.id
            treinoExistente
        } else {
            val nuevoId = treinoRepository.save(
                TreinoEntity(
                    id = 0,
                    rutina_id = 0L, // Pendiente de asociar
                    timestamp = Instant.now().epochSecond,
                    done = false,
                    notas = null
                )
            )

            treinoIdActual = nuevoId
            treinoRepository.getById(nuevoId)!!
        }
    }

    private suspend fun cargarSeriesUI(
        detalles: List<ItemRutinaEntity>,
        treinoId: Long
    ): Map<Long, List<SerieUI>> {

        val itemsTreino = itemTreinoRepository.getAll()
            .filter { it.treino_id == treinoId }

        val mapa = mutableMapOf<Long, List<SerieUI>>()

        detalles.forEach { detalle ->
            val serieTreino = itemsTreino.filter { it.exercise_externalid == detalle.exercise_externalid }

            val listado = if (serieTreino.isEmpty()) {
                List(detalle.sets_amount) { index ->
                    SerieUI(
                        numero = index + 1,
                        carga = 0.0,
                        reps = 0,
                        unidad = "kg",
                        meta = construirMeta(detalle),
                        editable = true
                    )
                }
            } else {
                serieTreino.mapIndexed { index, it ->
                    SerieUI(
                        numero = index + 1,
                        carga = it.effective_load,
                        reps = it.effective_reps,
                        unidad = it.load_unit,
                        meta = construirMeta(detalle),
                        editable = false
                    )
                }
            }

            mapa[detalle.id] = listado
        }

        return mapa
    }

    private fun construirMeta(detalle: ItemRutinaEntity): String? {
        return when {
            detalle.reps_range_min != null && detalle.reps_range_max != null ->
                "${detalle.reps_range_min}-${detalle.reps_range_max} reps"

            detalle.reps_range_max != null ->
                "${detalle.reps_range_max} reps"

            else -> null
        }
    }
}

// ---------------- DTO UI CLEAN ----------------

data class SerieUI(
    val numero: Int,
    val carga: Double,
    val reps: Int,
    val unidad: String,
    val meta: String?,
    val editable: Boolean
)

// ---------------- MAPPERS ----------------

private fun RutinaEntity.toDomain(): Rutina = Rutina(
    id = id,
    nombre = name,
    descripcion = desc,
    dia = dia?.let { DayOfWeek.valueOf(it) }
)
