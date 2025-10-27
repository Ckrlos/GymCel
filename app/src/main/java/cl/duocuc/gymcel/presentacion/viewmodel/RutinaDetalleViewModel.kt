package cl.duocuc.gymcel.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duocuc.gymcel.data.local.entities.relations.RutinaConEjercicios
import cl.duocuc.gymcel.domain.usecase.ObtenerRutinaConEjerciciosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RutinaDetalleViewModel(
    private val obtenerRutinaConEjerciciosUseCase: ObtenerRutinaConEjerciciosUseCase
) : ViewModel() {

    private val _detalle = MutableStateFlow<RutinaConEjercicios?>(null)
    val detalle: StateFlow<RutinaConEjercicios?> = _detalle

    fun cargarDetalle(rutinaId: Int) {
        viewModelScope.launch {
            _detalle.value = obtenerRutinaConEjerciciosUseCase(rutinaId)
        }
    }
}
