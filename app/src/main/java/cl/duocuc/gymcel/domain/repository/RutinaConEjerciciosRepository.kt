package cl.duocuc.gymcel.domain.repository

import cl.duocuc.gymcel.data.local.entities.relations.RutinaConEjercicios

interface RutinaConEjerciciosRepository {
    suspend fun obtenerRutinaConTodo(id: Int): RutinaConEjercicios?
    suspend fun obtenerTodasConTodo(): List<RutinaConEjercicios>
}
