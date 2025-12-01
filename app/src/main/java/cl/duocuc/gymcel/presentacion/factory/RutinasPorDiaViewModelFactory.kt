package cl.duocuc.gymcel.presentacion.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.duocuc.gymcel.presentacion.viewmodel.RutinasPorDiaViewModel

class RutinasPorDiaViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RutinasPorDiaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RutinasPorDiaViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
