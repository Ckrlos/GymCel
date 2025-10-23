package cl.duocuc.gymcel.domain.repository

import cl.duocuc.gymcel.domain.model.Rutina

interface RutinaRepository {
    suspend fun obtenerRutinas(): List<Rutina>
    suspend fun guardarRutina(rutina: Rutina)
    suspend fun eliminarRutina(rutina: Rutina)
}
