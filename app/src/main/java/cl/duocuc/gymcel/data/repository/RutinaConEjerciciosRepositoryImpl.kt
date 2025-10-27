package cl.duocuc.gymcel.data.repository

import android.content.Context
import cl.duocuc.gymcel.Singleton
import cl.duocuc.gymcel.data.local.entities.relations.RutinaConEjercicios
import cl.duocuc.gymcel.domain.repository.RutinaConEjerciciosRepository

class RutinaConEjerciciosRepositoryImpl(context: Context) : RutinaConEjerciciosRepository {

    private val dao = Singleton.getDatabase(context).rutinaConEjerciciosDao()

    override suspend fun obtenerRutinaConTodo(id: Int): RutinaConEjercicios? {
        return dao.obtenerRutinaConTodo(id)
    }

    override suspend fun obtenerTodasConTodo(): List<RutinaConEjercicios> {
        return dao.obtenerTodasConTodo()
    }
}
