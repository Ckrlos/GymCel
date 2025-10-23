package cl.duocuc.gymcel.domain.repository

import cl.duocuc.gymcel.domain.model.Musculo

interface MusculoRepository {
    suspend fun obtenerMusculos(): List<Musculo>
    suspend fun guardarMusculo(musculo: Musculo)
}