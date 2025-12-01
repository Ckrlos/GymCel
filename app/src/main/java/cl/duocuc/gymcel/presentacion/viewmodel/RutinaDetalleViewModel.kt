package cl.duocuc.gymcel.presentacion.ui.viewmodels

import ItemTreinoDataService
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duocuc.gymcel.AppConstants
import cl.duocuc.gymcel.data.FactoryProvider
import cl.duocuc.gymcel.data.local.dao.ItemTreinoDao
import cl.duocuc.gymcel.data.local.db.GymDatabase
import cl.duocuc.gymcel.data.local.entities.ItemRutinaEntity
import cl.duocuc.gymcel.data.local.entities.ItemTreinoEntity
import cl.duocuc.gymcel.data.local.entities.RutinaEntity
import cl.duocuc.gymcel.data.local.entities.TreinoEntity
import cl.duocuc.gymcel.domain.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Instant

class RutinaDetalleViewModel : ViewModel() {
    private val db: GymDatabase by lazy {
        AppConstants.getInitializedDatabase() // ¡No requiere Context!
    }
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
    private val itemTreinoDao by lazy {
        FactoryProvider.daoFactory().create(ItemTreinoEntity::class.java) as ItemTreinoDao
    }
    private val itemTreinoService by lazy {
        ItemTreinoDataService(
            db = db,
            dao = itemTreinoDao
        )
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

    val popupUltimoTreino = MutableStateFlow<List<Pair<Double, Int>>?>(null)
    val popupEjercicio = MutableStateFlow<String?>(null)
    private var treinoIdActual: Long? = null
    private val _detallesRutina = MutableStateFlow<List<ItemRutinaEntity>>(emptyList())
    val detallesRutina: StateFlow<List<ItemRutinaEntity>> = _detallesRutina.asStateFlow()


    // ---------------- PUBLIC API ----------------

    fun cargarRutina(rutinaId: Long) {
        _loading.value = true

        viewModelScope.launch {
            try {
                val rutinaEntity = rutinaRepository.getById(rutinaId) ?: return@launch
                _rutina.value = rutinaEntity.toDomain()

                val detallesBase = cargarDetallesRutina(rutinaId)
                val treinoActivo = cargarOCrearTreino(rutinaId)
                _detallesRutina.value = detallesBase

                // Editable solo si el treino no está hecho
                _editable.value = !treinoActivo.done

                _seriesUI.value = cargarSeriesUI(detallesBase, treinoActivo)

            } finally {
                _loading.value = false
            }
        }
    }


    suspend fun obtenerUltimoTreino(detalle: ItemRutinaEntity): List<Pair<Double, Int>> {
        val ultimo = itemTreinoService.getUltimoTreinoPorEjercicio(detalle.exercise_externalid)
        return ultimo.map { it.effective_load to it.effective_reps }
    }


    fun mostrarUltimoTreino(detalle: ItemRutinaEntity) {
        viewModelScope.launch {
            val ultimo = obtenerUltimoTreino(detalle)
            Log.d("RutinaDetalle", "Datos para el popup: $ultimo") // Log para ver el contenido
            popupUltimoTreino.value = ultimo
            popupEjercicio.value = detalle.exercise_externalid
        }
    }




    fun cerrarPopupUltimoTreino() {
        popupUltimoTreino.value = null
    }

    fun actualizarCarga(detalleId: Long, serieIndex: Int, nuevaCarga: Double) {
        if (!_editable.value) return
        val copia = _seriesUI.value.toMutableMap()
        val lista = copia[detalleId]?.toMutableList() ?: return
        val serie = lista[serieIndex]
        lista[serieIndex] = serie.copy(carga = nuevaCarga)
        copia[detalleId] = lista
        _seriesUI.value = copia
    }

    fun actualizarReps(detalleId: Long, serieIndex: Int, nuevasReps: Int) {
        if (!_editable.value) return
        val copia = _seriesUI.value.toMutableMap()
        val lista = copia[detalleId]?.toMutableList() ?: return
        val serie = lista[serieIndex]
        lista[serieIndex] = serie.copy(reps = nuevasReps)
        copia[detalleId] = lista
        _seriesUI.value = copia
    }

    fun actualizarUnidad(detalleId: Long, serieIndex: Int, nuevaUnidad: UnidadPeso) {
        if (!_editable.value) return

        val copia = _seriesUI.value.toMutableMap()
        val lista = copia[detalleId]?.toMutableList() ?: return
        val serie = lista[serieIndex]

        val converted = nuevaUnidad.convert(
            value = serie.carga,
            sourceUnit = serie.unidad
        )

        lista[serieIndex] = serie.copy(
            unidad = nuevaUnidad,
            carga = converted
        )

        copia[detalleId] = lista
        _seriesUI.value = copia
    }

    fun guardarTreino() {
        val treinoId = treinoIdActual ?: return

        viewModelScope.launch {
            for ((detalleId, series) in _seriesUI.value) {
                val detalleEntity = itemRutinaRepository.getById(detalleId) ?: continue

                series.forEach { s ->
                    itemTreinoRepository.save(
                        ItemTreinoEntity(
                            id = 0,
                            treino_id = treinoId,
                            exercise_externalid = detalleEntity.exercise_externalid,
                            effective_reps = s.reps,
                            effective_load = s.carga,
                            load_unit = s.unidad.symbol,
                            rir = null,
                            rest_nanos = 0L
                        )
                    )
                    treinoRepository.update(
                        TreinoEntity(
                            id = treinoId,
                            rutina_id = detalleEntity.rutina_id,
                            timestamp = Instant.now().epochSecond,
                            done = true,
                            notas = null
                        )
                    )
                }
            }

            _editable.value = false
        }
    }


    // ---------------- INTERNALS ----------------

    private suspend fun cargarDetallesRutina(rutinaId: Long) =
        itemRutinaRepository.getAll().filter { it.rutina_id == rutinaId }

    private suspend fun cargarOCrearTreino(rutinaId: Long): TreinoEntity {
        val existente = treinoRepository.getAll().find { it.rutina_id == rutinaId }
        if (existente != null) {
            treinoIdActual = existente.id
            return existente
        }

        val nuevoId = treinoRepository.save(
            TreinoEntity(
                id = 0,
                rutina_id = rutinaId,
                timestamp = Instant.now().epochSecond,
                done = false,
                notas = null
            )
        )

        treinoIdActual = nuevoId
        return treinoRepository.getById(nuevoId)!!
    }

    private suspend fun cargarSeriesUI(
        detalles: List<ItemRutinaEntity>,
        treino: TreinoEntity
    ): Map<Long, List<SerieUI>> {

        val itemsTreino = itemTreinoRepository.getAll()
            .filter { it.treino_id == treino.id }

        val editableGlobal = !treino.done
        val resultado = mutableMapOf<Long, List<SerieUI>>()

        detalles.forEach { detalle ->

            val seriesGuardadas =
                itemsTreino.filter { it.exercise_externalid == detalle.exercise_externalid }

            val listaUI = if (seriesGuardadas.isEmpty()) {
                List(detalle.sets_amount) { index ->
                    SerieUI(
                        numero = index + 1,
                        carga = 0.0,
                        reps = 0,
                        unidad = UnidadPeso.KILOGRAM,
                        meta = construirMeta(detalle),
                        editable = editableGlobal
                    )
                }
            } else {
                seriesGuardadas.mapIndexed { index, it ->
                    SerieUI(
                        numero = index + 1,
                        carga = it.effective_load,
                        reps = it.effective_reps,
                        unidad = UnidadPeso.values().find { u -> u.symbol == it.load_unit }
                            ?: UnidadPeso.KILOGRAM,
                        meta = construirMeta(detalle),
                        editable = editableGlobal
                    )
                }
            }

            resultado[detalle.id] = listaUI
        }

        return resultado
    }


    private fun construirMeta(detalle: ItemRutinaEntity): String? = when {
        detalle.reps_goal != null ->
            "${detalle.reps_goal} reps"  // Si hay un objetivo de reps, lo usamos.

        detalle.reps_range_min != null && detalle.reps_range_max != null ->
            "${detalle.reps_range_min}-${detalle.reps_range_max} reps"  // Rango de reps.

        detalle.reps_range_max != null ->
            "${detalle.reps_range_max} reps"  // Si solo hay un `reps_range_max`.

        else -> null  // Si no hay ninguno de los valores.
    }

}


// ---------------- DTO UI CLEAN ----------------
data class SerieUI(
    val numero: Int,
    val carga: Double,
    val reps: Int,
    val unidad: UnidadPeso,
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
