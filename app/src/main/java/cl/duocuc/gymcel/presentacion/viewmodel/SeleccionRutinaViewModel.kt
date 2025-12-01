package cl.duocuc.gymcel.presentacion.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duocuc.gymcel.data.FactoryProvider
import cl.duocuc.gymcel.data.local.dao.GymcelDao
import cl.duocuc.gymcel.data.local.db.GymDatabase
import cl.duocuc.gymcel.data.local.entities.RutinaEntity
import cl.duocuc.gymcel.domain.model.Rutina
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

class SeleccionarRutinaViewModel(
    db: GymDatabase
) : ViewModel() {

    private val registry = FactoryProvider.registry(db)

    private val rutinaRepository = FactoryProvider.repositoryFactory(registry)
        .create(RutinaEntity::class.java)

    private val _rutinas = MutableStateFlow<List<Rutina>>(emptyList())
    val rutinas: StateFlow<List<Rutina>> = _rutinas.asStateFlow()

    private val _rutinaSeleccionada = MutableStateFlow<Rutina?>(null)
    val rutinaSeleccionada: StateFlow<Rutina?> = _rutinaSeleccionada.asStateFlow()

    private val _dropdownExpanded = MutableStateFlow(false)
    val dropdownExpanded: StateFlow<Boolean> = _dropdownExpanded.asStateFlow()

    init {
        cargarRutinas()
    }

    private fun cargarRutinas() {
        viewModelScope.launch {
            val entidades: List<RutinaEntity> = rutinaRepository.getAll()
            val modelos = entidades.mapNotNull { entidad ->
                // Convertir 'dia' de String? a DayOfWeek
                val dia = entidad.dia?.toIntOrNull()?.takeIf { it in 1..7 }?.let { DayOfWeek.of(it) }

                Rutina(
                    id = entidad.id,
                    nombre = entidad.name,
                    descripcion = entidad.desc,
                    dia = dia,
                    detalleRutina = emptyList()
                )
            }
            _rutinas.value = ordenarPorDiaMasCercano(modelos)
        }
    }

    fun seleccionarRutina(rutina: Rutina) {
        _rutinaSeleccionada.value = rutina
        cerrarDropdown()
    }

    fun abrirDropdown() {
        _dropdownExpanded.value = true
    }

    fun cerrarDropdown() {
        _dropdownExpanded.value = false
    }

    private fun ordenarPorDiaMasCercano(rutinas: List<Rutina>): List<Rutina> {
        val hoy = LocalDate.now().dayOfWeek.value
        return rutinas.sortedBy { rutina ->
            val index = rutina.dia?.value ?: 0
            (index - hoy + 7) % 7
        }
    }
}
