package cl.duocuc.gymcel.domain.repository

import cl.duocuc.gymcel.domain.model.Musculo

interface MusculoRepository {
    fun obtenerMusculos(): List<Musculo>
    fun guardarMusculo(musculo: Musculo)
}