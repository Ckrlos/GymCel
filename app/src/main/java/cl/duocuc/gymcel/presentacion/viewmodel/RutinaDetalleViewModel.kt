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
import java.time.DayOfWeek
import java.util.UUID

class RutinaDetalleViewModel : ViewModel() {

    private val _rutina = MutableStateFlow<Rutina?>(null)
    val rutina: StateFlow<Rutina?> = _rutina.asStateFlow()

    private val _ejercicios = MutableStateFlow<List<Ejercicio>>(emptyList())
    val ejercicios: StateFlow<List<Ejercicio>> = _ejercicios.asStateFlow()

    private val _historialVisible = MutableStateFlow<Int?>(null)
    val historialVisible: StateFlow<Int?> = _historialVisible.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    fun cargarRutina(rutinaId: Long) {
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

    private fun crearRutinaFicticia(rutinaId: Long): Rutina {
        return when (rutinaId) {
            1L -> Rutina(1, "Pecho + Tríceps", "Rutina de fuerza para pecho y tríceps", DayOfWeek.MONDAY)
            2L -> Rutina(2, "Espalda + Bíceps", "Rutina de fuerza para espalda y bíceps", DayOfWeek.WEDNESDAY)
            3L -> Rutina(3, "Pierna Full", "Rutina completa para piernas", DayOfWeek.FRIDAY)
            else -> Rutina(rutinaId, "Rutina Personalizada", "Rutina personalizada", null)
        }
    }

    private fun crearEjerciciosFicticios(rutinaId: Long): List<Ejercicio> {
        val id: String = UUID.randomUUID().toString()
        return when (rutinaId) {
            1L -> listOf(
                Ejercicio(id, "Press Banca"),
                Ejercicio(id, "Fondos en Paralelas"),
                Ejercicio(id, "Extensiones de Tríceps")
            )
            2L -> listOf(
                Ejercicio(id, "Dominadas"),
                Ejercicio(id, "Curl de Bíceps")
            )
            else -> listOf(
                Ejercicio(id, "Sentadillas")
            )
        }
    }

    //FIXME: ejercicioId es String...
    fun mostrarHistorial(ejercicioId: Int) {
        _historialVisible.value = ejercicioId
    }

    fun ocultarHistorial() {
        _historialVisible.value = null
    }
}