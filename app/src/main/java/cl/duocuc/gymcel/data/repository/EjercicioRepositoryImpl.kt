package cl.duocuc.gymcel.data.repository

import android.content.Context
import cl.duocuc.gymcel.Singleton
import cl.duocuc.gymcel.data.mapper.toDomain
import cl.duocuc.gymcel.data.mapper.toEntity
import cl.duocuc.gymcel.domain.model.Ejercicio
import cl.duocuc.gymcel.domain.repository.EjercicioRepository

class EjercicioRepositoryImpl(context: Context) : EjercicioRepository {

    private val dao = Singleton.getDatabase(context).ejercicioDao()

    override suspend fun obtenerEjercicios(): List<Ejercicio> {
        return dao.getAll().map { it.toDomain() }
    }

    override suspend fun guardarEjercicio(ejercicio: Ejercicio) {
        dao.insert(ejercicio.toEntity())
    }

    override suspend fun eliminarEjercicio(ejercicio: Ejercicio) {
        dao.delete(ejercicio.toEntity())
    }
}
