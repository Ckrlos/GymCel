package cl.duocuc.gymcel.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import cl.duocuc.gymcel.data.local.entities.relations.RutinaConEjercicios

@Dao
interface RutinaConEjerciciosDao {

    @Transaction
    @Query("SELECT * FROM rutina WHERE id = :rutinaId")
    suspend fun obtenerRutinaConTodo(rutinaId: Int): RutinaConEjercicios?

    @Transaction
    @Query("SELECT * FROM rutina")
    suspend fun obtenerTodasConTodo(): List<RutinaConEjercicios>
}
