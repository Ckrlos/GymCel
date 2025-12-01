package cl.duocuc.gymcel.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duocuc.gymcel.data.FactoryProvider
import cl.duocuc.gymcel.data.local.entities.RutinaEntity
import cl.duocuc.gymcel.data.local.entities.TreinoEntity
import cl.duocuc.gymcel.domain.model.Rutina
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek

class RutinasPorDiaViewModel : ViewModel() {

    private val rutinaRepo by lazy {
        FactoryProvider.repositoryFactory().create(RutinaEntity::class.java)
    }

    private val treinoRepo by lazy {
        FactoryProvider.repositoryFactory().create(TreinoEntity::class.java)
    }

    private val _rutinas = MutableStateFlow<List<Rutina>>(emptyList())
    val rutinas = _rutinas.asStateFlow()

    private val _treinosPorRutina = MutableStateFlow<Map<Long, Int>>(emptyMap())
    val treinosPorRutina = _treinosPorRutina.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {

            // -------------------------------
            // 1) Cargar todos los treinos
            // -------------------------------
            val treinos = treinoRepo.getAll()

            // Agrupar treinos por rutina_id (solo las que existen en historial)
            val mapaTreinos = treinos.groupBy { it.rutina_id }
                .mapValues { it.value.size }

            _treinosPorRutina.value = mapaTreinos

            // Si una rutina NO aparece en treinos → NO SE MUESTRA
            val idsRutinasUsadas = mapaTreinos.keys.filter { it != null }.map { it!! }

            // -------------------------------
            // 2) Cargar solo esas rutinas
            // -------------------------------
            val rutinasEntities = rutinaRepo.getAll()
                .filter { it.id in idsRutinasUsadas }

            val modelos = rutinasEntities.mapNotNull { entidad ->

                val dia = try {
                    entidad.dia?.let { DayOfWeek.valueOf(it) }
                } catch (_: Exception) {
                    null
                }

                Rutina(
                    id = entidad.id,
                    nombre = entidad.name,
                    descripcion = entidad.desc,
                    dia = dia
                )
            }

            _rutinas.value = modelos
        }
    }
}
