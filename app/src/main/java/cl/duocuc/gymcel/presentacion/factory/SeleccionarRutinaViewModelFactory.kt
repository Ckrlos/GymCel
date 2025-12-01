package cl.duocuc.gymcel.presentacion.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.duocuc.gymcel.data.local.db.GymDatabase
import cl.duocuc.gymcel.presentacion.viewmodel.SeleccionarRutinaViewModel

class SeleccionarRutinaViewModelFactory(private val db: GymDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SeleccionarRutinaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SeleccionarRutinaViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
