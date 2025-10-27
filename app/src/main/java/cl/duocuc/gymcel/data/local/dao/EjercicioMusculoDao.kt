package cl.duocuc.gymcel.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import cl.duocuc.gymcel.data.local.entities.EjercicioMusculoCrossRef

@Dao
interface EjercicioMusculoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(crossRef: EjercicioMusculoCrossRef)
}
