package cl.duocuc.gymcel.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duocuc.gymcel.domain.model.Ejercicio
import cl.duocuc.gymcel.domain.usecase.ObtenerEjerciciosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class EjercicioViewModel(
    private val obtenerEjerciciosUseCase: ObtenerEjerciciosUseCase
) : ViewModel() {

    val ejercicios = MutableStateFlow<List<Ejercicio>>(emptyList())

    init {
        cargarEjercicios()
    }

    private fun cargarEjercicios() {
        viewModelScope.launch {
            ejercicios.value = obtenerEjerciciosUseCase()
        }
    }
}
