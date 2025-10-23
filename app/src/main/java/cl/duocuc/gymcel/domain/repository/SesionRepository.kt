package cl.duocuc.gymcel.domain.repository

import cl.duocuc.gymcel.domain.model.Sesion

interface SesionRepository {
    suspend fun obtenerSesiones(): List<Sesion>
    suspend fun guardarSesion(sesion: Sesion)
    suspend fun eliminarSesion(sesion: Sesion)
}
