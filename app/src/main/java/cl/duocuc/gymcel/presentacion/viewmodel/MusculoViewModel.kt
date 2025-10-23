package cl.duocuc.gymcel.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import cl.duocuc.gymcel.domain.model.Musculo
import cl.duocuc.gymcel.domain.usecase.ObtenerMusculosUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class MusculoViewModel(
    private val obtenerMusculosUseCase: ObtenerMusculosUseCase
) : ViewModel() {

    val musculos = MutableStateFlow<List<Musculo>>(emptyList())

    fun cargarMusculos() {
        musculos.value = obtenerMusculosUseCase()
    }
}
