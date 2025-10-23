package cl.duocuc.gymcel.data.local.dao


import androidx.room.*
import cl.duocuc.gymcel.data.local.entities.SesionEntity

@Dao
interface SesionDao {
    @Query("SELECT * FROM sesion")
    suspend fun getAll(): List<SesionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sesion: SesionEntity)

    @Delete
    suspend fun delete(sesion: SesionEntity)
}