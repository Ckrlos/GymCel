// presentacion/ui/viewmodels/RutinaDetalleViewModel.kt
package cl.duocuc.gymcel.presentacion.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duocuc.gymcel.domain.model.Ejercicio
import cl.duocuc.gymcel.domain.model.Rutina
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RutinaDetalleViewModel : ViewModel() {

    private val _rutina = MutableStateFlow<Rutina?>(null)
    val rutina: StateFlow<Rutina?> = _rutina.asStateFlow()

    private val _ejercicios = MutableStateFlow<List<Ejercicio>>(emptyList())
    val ejercicios: StateFlow<List<Ejercicio>> = _ejercicios.asStateFlow()

    private val _historialVisible = MutableStateFlow<Int?>(null)
    val historialVisible: StateFlow<Int?> = _historialVisible.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    fun cargarRutina(rutinaId: Int) {
        _cargando.value = true
        viewModelScope.launch {
            try {
                // Simular carga de datos
                kotlinx.coroutines.delay(500)

                val rutinaFicticia = crearRutinaFicticia(rutinaId)
                val ejerciciosFicticios = crearEjerciciosFicticios(rutinaId)

                _rutina.value = rutinaFicticia
                _ejercicios.value = ejerciciosFicticios
            } catch (e: Exception) {
                e.printStackTrace()
                val rutinaFicticia = crearRutinaFicticia(rutinaId)
                val ejerciciosFicticios = crearEjerciciosFicticios(rutinaId)
                _rutina.value = rutinaFicticia
                _ejercicios.value = ejerciciosFicticios
            } finally {
                _cargando.value = false
            }
        }
    }

    private fun crearRutinaFicticia(rutinaId: Int): Rutina {
        return when (rutinaId) {
            1 -> Rutina(1, "Pecho + Tríceps", "Rutina de fuerza para pecho y tríceps", "Lunes")
            2 -> Rutina(2, "Espalda + Bíceps", "Rutina de fuerza para espalda y bíceps", "Miércoles")
            3 -> Rutina(3, "Pierna Full", "Rutina completa para piernas", "Viernes")
            else -> Rutina(rutinaId, "Rutina Personalizada", "Rutina personalizada", "Personalizado")
        }
    }

    private fun crearEjerciciosFicticios(rutinaId: Int): List<Ejercicio> {
        return when (rutinaId) {
            1 -> listOf(
                Ejercicio(1, "Press Banca", "Ejercicio para pectorales", null, "Fuerza", "Barra", 3),
                Ejercicio(2, "Fondos en Paralelas", "Ejercicio para tríceps y pectoral inferior", null, "Fuerza", "Paralelas", 2),
                Ejercicio(3, "Extensiones de Tríceps", "Aislamiento de tríceps", null, "Aislamiento", "Polea", 1)
            )
            2 -> listOf(
                Ejercicio(4, "Dominadas", "Ejercicio para espalda", null, "Fuerza", "Barra fija", 4),
                Ejercicio(5, "Curl de Bíceps", "Aislamiento de bíceps", null, "Aislamiento", "Mancuernas", 1)
            )
            else -> listOf(
                Ejercicio(6, "Sentadillas", "Ejercicio para piernas", null, "Fuerza", "Barra", 3)
            )
        }
    }

    fun mostrarHistorial(ejercicioId: Int) {
        _historialVisible.value = ejercicioId
    }

    fun ocultarHistorial() {
        _historialVisible.value = null
    }
}