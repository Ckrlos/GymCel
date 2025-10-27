package cl.duocuc.gymcel.domain.repository

import cl.duocuc.gymcel.domain.model.Ejercicio
import cl.duocuc.gymcel.domain.model.Musculo
import cl.duocuc.gymcel.domain.model.Rutina

interface RutinaRepository {
    suspend fun obtenerRutinas(): List<Rutina>
    suspend fun guardarRutina(rutina: Rutina)
    suspend fun eliminarRutina(rutina: Rutina)

    // ➕ Nuevas funciones que también usas
    suspend fun guardarRutinaYObtenerId(rutina: Rutina): Long

    suspend fun guardarEjercicioCompleto(
        rutinaId: Int,
        ejercicio: Ejercicio,
        musculos: List<Musculo>,
        series: Int,
        reps: Int,
        carga: Double,
        descanso: Int
    )
}