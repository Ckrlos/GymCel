package cl.duocuc.gymcel.domain.usecase

import cl.duocuc.gymcel.domain.model.Musculo
import cl.duocuc.gymcel.domain.repository.MusculoRepository

class GuardarMusculoUseCase(private val repo: MusculoRepository) {
    suspend operator fun invoke(musculo: Musculo) = repo.guardarMusculo(musculo)
}
