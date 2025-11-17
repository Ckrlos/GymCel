package cl.duocuc.gymcel.presentacion.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duocuc.gymcel.data.local.entities.RutinaEntryEntity
import cl.duocuc.gymcel.data.repository.EjercicioRepositoryImpl
import cl.duocuc.gymcel.domain.model.Ejercicio
import cl.duocuc.gymcel.domain.model.Musculo
import cl.duocuc.gymcel.domain.model.Rutina
import cl.duocuc.gymcel.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RutinaViewModel(
    private val context: Context,
    private val obtenerRutinasUseCase: ObtenerRutinasUseCase,
    private val guardarRutinaUseCase: GuardarRutinaUseCase,
    private val agregarEjercicioUseCase: AgregarEjercicioARutinaUseCase,
    private val guardarRutinaYObtenerIdUseCase: GuardarRutinaYObtenerIdUseCase,
    private val guardarEjercicioCompletoUseCase: GuardarEjercicioCompletoUseCase
) : ViewModel() {

    private val _rutinas = MutableStateFlow<List<Rutina>>(emptyList())
    val rutinas: StateFlow<List<Rutina>> = _rutinas

    init {
        cargarRutinas()
    }

    fun cargarRutinas() {
        viewModelScope.launch {
            _rutinas.value = obtenerRutinasUseCase()
        }
    }

    fun guardarRutina(rutina: Rutina) {
        viewModelScope.launch {
            guardarRutinaUseCase(rutina)
            cargarRutinas()
        }
    }


    fun agregarEjercicioARutina(rutinaId: Int, ejercicio: Ejercicio) {
        viewModelScope.launch {

            val ejercicioRepo = EjercicioRepositoryImpl(context)
            val ejercicioId = ejercicioRepo.guardarEjercicioYObtenerId(ejercicio).toInt()

            val entry = RutinaEntryEntity(
                rutina_id = rutinaId,
                ejercicio_id = ejercicioId,
                name = ejercicio.nombre,
                sets_amount = 3,
                set_range_min = 8,
                set_range_max = 12
            )
            agregarEjercicioUseCase(entry)
            cargarRutinas()
        }
    }

    fun guardarRutinaYObtenerId(rutina: Rutina, onSaved: (Long) -> Unit) {
        viewModelScope.launch {
            val id = guardarRutinaYObtenerIdUseCase(rutina) // este llama al nuevo use case
            onSaved(id)
        }
    }

    fun guardarEjercicioCompleto(
        rutinaId: Int,
        ejercicio: Ejercicio,
        musculos: List<Musculo>,
        series: Int,
        reps: Int,
        carga: Double,
        descanso: Int
    ) {
        viewModelScope.launch {
            guardarEjercicioCompletoUseCase(
                rutinaId, ejercicio, musculos, series, reps, carga, descanso
            )
            cargarRutinas()
        }
    }


}
