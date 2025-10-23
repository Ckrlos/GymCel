package cl.duocuc.gymcel.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duocuc.gymcel.domain.model.Musculo
import cl.duocuc.gymcel.domain.usecase.GuardarMusculoUseCase
import cl.duocuc.gymcel.domain.usecase.ObtenerMusculosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MusculoViewModel(
    private val obtenerMusculosUseCase: ObtenerMusculosUseCase,
    private val guardarMusculoUseCase: GuardarMusculoUseCase
) : ViewModel() {

    // flujo observable de músculos
    val musculos = MutableStateFlow<List<Musculo>>(emptyList())

    // 🔹 Este bloque se ejecuta automáticamente al crear el ViewModel
    init {
        cargarMusculos()
    }

    // 🔹 Lógica de carga desde el caso de uso
    fun cargarMusculos() {
        viewModelScope.launch {
            musculos.value = obtenerMusculosUseCase()
        }
    }
    fun guardarMusculo(musculo: Musculo) {
        viewModelScope.launch(Dispatchers.IO) {
            guardarMusculoUseCase(musculo)
            cargarMusculos() // actualiza la lista
        }
    }
}