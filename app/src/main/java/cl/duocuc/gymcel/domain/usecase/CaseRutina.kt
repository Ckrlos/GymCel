package cl.duocuc.gymcel.domain.usecase

import cl.duocuc.gymcel.domain.model.Rutina

class ObtenerRutinasUseCase(private val repository: RutinaRepository) {
    suspend operator fun invoke(): List<Rutina> = repository.obtenerRutinas()
}

class GuardarRutinaUseCase(private val repository: RutinaRepository) {
    suspend operator fun invoke(rutina: Rutina) = repository.guardarRutina(rutina)
}
class GuardarRutinaYObtenerIdUseCase(private val repository: RutinaRepositoryImpl) {
    suspend operator fun invoke(rutina: Rutina): Long {
        return repository.guardarRutinaYObtenerId(rutina)
    }
}
