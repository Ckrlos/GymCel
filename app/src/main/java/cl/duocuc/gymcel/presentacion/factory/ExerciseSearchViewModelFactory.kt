package cl.duocuc.gymcel.presentacion.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.duocuc.gymcel.data.api.ExerciseDbApiService
import cl.duocuc.gymcel.presentacion.ui.ejercicio.ExerciseSearchViewModel

class ExerciseSearchViewModelFactory(
    private val apiService: ExerciseDbApiService
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseSearchViewModel::class.java)) {
            return ExerciseSearchViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}