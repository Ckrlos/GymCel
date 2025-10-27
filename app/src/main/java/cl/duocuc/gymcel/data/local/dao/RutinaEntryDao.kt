package cl.duocuc.gymcel.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.duocuc.gymcel.data.local.entities.RutinaEntryEntity

@Dao
interface RutinaEntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: RutinaEntryEntity)

    @Query("SELECT * FROM rutina_entry WHERE rutina_id = :rutinaId")
    suspend fun getEntriesByRutinaId(rutinaId: Int): List<RutinaEntryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAndReturnId(entry: RutinaEntryEntity): Long
}
