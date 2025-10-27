package cl.duocuc.gymcel.presentacion.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.duocuc.gymcel.domain.usecase.ObtenerRutinasConEjerciciosUseCase
import cl.duocuc.gymcel.presentacion.viewmodel.SemanaViewModel

class SemanaViewModelFactory(
    private val obtenerRutinasConEjerciciosUseCase: ObtenerRutinasConEjerciciosUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SemanaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SemanaViewModel(obtenerRutinasConEjerciciosUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
