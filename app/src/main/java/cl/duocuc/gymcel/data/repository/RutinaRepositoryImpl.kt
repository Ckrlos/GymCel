package cl.duocuc.gymcel.data.repository

import android.content.Context
import cl.duocuc.gymcel.Singleton
import cl.duocuc.gymcel.data.mapper.toDomain
import cl.duocuc.gymcel.data.mapper.toEntity
import cl.duocuc.gymcel.domain.model.Rutina
import cl.duocuc.gymcel.domain.repository.RutinaRepository

class RutinaRepositoryImpl(context: Context) : RutinaRepository {

    private val dao = Singleton.getDatabase(context).rutinaDao()

    override suspend fun obtenerRutinas(): List<Rutina> {
        return dao.getAll().map { it.toDomain() }
    }

    override suspend fun guardarRutina(rutina: Rutina) {
        dao.insert(rutina.toEntity())
    }

    override suspend fun eliminarRutina(rutina: Rutina) {
        dao.delete(rutina.toEntity())
    }
}
