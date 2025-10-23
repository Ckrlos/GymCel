package cl.duocuc.gymcel.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.duocuc.gymcel.data.local.entities.EjercicioEntity

@Dao
interface EjercicioDao {
    @Query("SELECT * FROM ejercicio")
    suspend fun getAll(): List<EjercicioEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ejercicio: EjercicioEntity)

    @Delete
    suspend fun delete(ejercicio: EjercicioEntity)
}