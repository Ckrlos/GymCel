package cl.duocuc.gymcel.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import cl.duocuc.gymcel.data.local.entities.SetRutinaEntity

@Dao
interface SetRutinaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(set: SetRutinaEntity)
}
