package cl.duocuc.gymcel.presentacion.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.duocuc.gymcel.domain.usecase.AgregarEjercicioARutinaUseCase
import cl.duocuc.gymcel.domain.usecase.GuardarEjercicioCompletoUseCase
import cl.duocuc.gymcel.domain.usecase.GuardarRutinaUseCase
import cl.duocuc.gymcel.domain.usecase.GuardarRutinaYObtenerIdUseCase
import cl.duocuc.gymcel.domain.usecase.ObtenerRutinasUseCase
import cl.duocuc.gymcel.presentacion.viewmodel.RutinaViewModel

class RutinaViewModelFactory(
    private val context: Context,
    private val obtenerRutinasUseCase: ObtenerRutinasUseCase,
    private val guardarRutinaUseCase: GuardarRutinaUseCase,
    private val guardarRutinaYObtenerIdUseCase: GuardarRutinaYObtenerIdUseCase,
    private val agregarEjercicioARutinaUseCase: AgregarEjercicioARutinaUseCase,
    private val guardarEjercicioCompletoUseCase: GuardarEjercicioCompletoUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RutinaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RutinaViewModel(
                context,
                obtenerRutinasUseCase,
                guardarRutinaUseCase,
                agregarEjercicioARutinaUseCase,
                guardarRutinaYObtenerIdUseCase,
                guardarEjercicioCompletoUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}