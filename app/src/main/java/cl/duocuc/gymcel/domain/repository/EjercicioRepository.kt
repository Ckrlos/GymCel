package cl.duocuc.gymcel.domain.repository

import cl.duocuc.gymcel.domain.model.Ejercicio

interface EjercicioRepository {
    suspend fun obtenerEjercicios(): List<Ejercicio>
    suspend fun guardarEjercicio(ejercicio: Ejercicio)
    suspend fun eliminarEjercicio(ejercicio: Ejercicio)
    suspend fun guardarEjercicioYObtenerId(ejercicio: Ejercicio): Long
}
