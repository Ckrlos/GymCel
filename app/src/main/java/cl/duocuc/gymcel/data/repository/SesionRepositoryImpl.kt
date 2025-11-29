package cl.duocuc.gymcel.data.repository

import android.content.Context
import cl.duocuc.gymcel.AppConstants
import cl.duocuc.gymcel.data.mapper.toDomain
import cl.duocuc.gymcel.data.mapper.toEntity
import cl.duocuc.gymcel.domain.model.Sesion
import cl.duocuc.gymcel.domain.repository.SesionRepository

class SesionRepositoryImpl(context: Context) : SesionRepository {

    private val dao = AppConstants.getDatabase(context).sesionDao()

    override suspend fun obtenerSesiones(): List<Sesion> {
        return dao.getAll().map { it.toDomain() }
    }

    override suspend fun guardarSesion(sesion: Sesion) {
        dao.insert(sesion.toEntity())
    }

    override suspend fun eliminarSesion(sesion: Sesion) {
        dao.delete(sesion.toEntity())
    }
}
