package cl.duocuc.gymcel.presentacion.factory

import androidx.lifecycle.ViewModelProvider
import cl.duocuc.gymcel.data.api.ExerciseDbApiService
import cl.duocuc.gymcel.presentacion.viewmodel.ExerciseDetailViewModel

class ExerciseDetailViewModelFactory(
    private val apiService: ExerciseDbApiService
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseDetailViewModel::class.java)) {
            return ExerciseDetailViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}