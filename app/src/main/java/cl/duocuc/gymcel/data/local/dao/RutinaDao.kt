package cl.duocuc.gymcel.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.duocuc.gymcel.data.local.entities.RutinaEntity


@Dao
interface RutinaDao {
    @Query("SELECT * FROM rutina")
    suspend fun getAll(): List<RutinaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rutina: RutinaEntity)

    @Delete
    suspend fun delete(rutina: RutinaEntity)
}