package cl.duocuc.gymcel.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duocuc.gymcel.data.local.entities.relations.RutinaConEjercicios
import cl.duocuc.gymcel.domain.usecase.ObtenerRutinasConEjerciciosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SemanaViewModel(
    private val obtenerRutinasConEjerciciosUseCase: ObtenerRutinasConEjerciciosUseCase
) : ViewModel() {

    private val _rutinas = MutableStateFlow<List<RutinaConEjercicios>>(emptyList())
    val rutinas: StateFlow<List<RutinaConEjercicios>> = _rutinas

    init {
        cargarRutinas()
    }

    fun cargarRutinas() {
        viewModelScope.launch {
            _rutinas.value = obtenerRutinasConEjerciciosUseCase()
        }
    }
}
