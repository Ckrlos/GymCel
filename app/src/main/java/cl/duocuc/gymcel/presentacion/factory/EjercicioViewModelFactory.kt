package cl.duocuc.gymcel.presentacion.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.duocuc.gymcel.domain.model.Ejercicio
import cl.duocuc.gymcel.domain.repository.EjercicioRepository
import cl.duocuc.gymcel.presentacion.viewmodel.EjercicioViewModel

class EjercicioViewModelFactory(
    private val obtenerEjerciciosUseCase: ObtenerEjerciciosUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EjercicioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EjercicioViewModel(obtenerEjerciciosUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
class ObtenerEjerciciosUseCase(private val repo: EjercicioRepository) {
    suspend operator fun invoke(): List<Ejercicio> = repo.obtenerEjercicios()
}
