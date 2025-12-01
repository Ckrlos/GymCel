package cl.duocuc.gymcel.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duocuc.gymcel.data.local.entities.RutinaEntity
import cl.duocuc.gymcel.data.local.entities.TreinoEntity
import cl.duocuc.gymcel.domain.data.RepositoryFactory
import cl.duocuc.gymcel.domain.model.Rutina
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek

class WorkoutLogViewModel(
    repoFactory: RepositoryFactory
) : ViewModel() {

    private val rutinaRepo by lazy {
        repoFactory.create(RutinaEntity::class.java)
    }

    private val treinoRepo by lazy {
        repoFactory.create(TreinoEntity::class.java)
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
            // 1) Cargar todos los treinos completados (done = true)
            // -------------------------------
            val treinos = treinoRepo.getAll().filter { it.done }  // Filtrar solo los treinos completos

            // Agrupar los treinos completados por rutina_id
            val mapaTreinos = treinos.groupBy { it.rutina_id }
                .mapValues { it.value.size }

            _treinosPorRutina.value = mapaTreinos

            // Si una rutina NO aparece en treinos completados → NO SE MUESTRA
            val idsRutinasUsadas = mapaTreinos.keys.filter { it != null }.map { it!! }

            // -------------------------------
            // 2) Cargar solo las rutinas con treinos completados
            // -------------------------------
            val rutinasEntities = rutinaRepo.getAll()
                .filter { it.id in idsRutinasUsadas }  // Solo rutinas que tienen treinos completados

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
