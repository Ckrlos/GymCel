// presentacion/ui/viewmodels/SeleccionarRutinaViewModel.kt
package cl.duocuc.gymcel.presentacion.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duocuc.gymcel.domain.model.Rutina
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

class SeleccionarRutinaViewModel : ViewModel() {

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
            // Usar datos ficticios
            val rutinasFicticias = listOf(
                Rutina(1, "Pecho + Tríceps", "Fuerza", DayOfWeek.MONDAY),
                Rutina(2, "Espalda + Bíceps", "Fuerza", DayOfWeek.WEDNESDAY),
                Rutina(3, "Pierna Full", "Potencia", DayOfWeek.FRIDAY)
            )
            _rutinas.value = ordenarPorDiaMasCercano(rutinasFicticias)
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

        val hoy = LocalDate.now().dayOfWeek.value % 7

        return rutinas.sortedBy { rutina ->
            val index = DayOfWeek.entries.indexOf(rutina.dia)
            val distancia = (index - hoy + 7) % 7
            distancia
        }
    }
}