package cl.duocuc.gymcel.presentacion.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.duocuc.gymcel.domain.usecase.GuardarMusculoUseCase
import cl.duocuc.gymcel.domain.usecase.ObtenerMusculosUseCase
import cl.duocuc.gymcel.presentacion.viewmodel.MusculoViewModel

class MusculoViewModelFactory(
    private val obtenerMusculosUseCase: ObtenerMusculosUseCase,
    private val guardarMusculoUseCase: GuardarMusculoUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MusculoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MusculoViewModel(obtenerMusculosUseCase, guardarMusculoUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
