package cl.duocuc.gymcel.domain.usecase

import cl.duocuc.gymcel.domain.model.Musculo
import cl.duocuc.gymcel.domain.repository.MusculoRepository

class ObtenerMusculosUseCase(private val repo: MusculoRepository) {
    suspend operator fun invoke(): List<Musculo> = repo.obtenerMusculos()
}