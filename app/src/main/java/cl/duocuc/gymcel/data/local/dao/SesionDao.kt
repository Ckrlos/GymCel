package cl.duocuc.gymcel.data.local.dao


import androidx.room.*
import cl.duocuc.gymcel.data.local.entities.SesionEntity

@Dao
interface SesionDao {
    @Query("SELECT * FROM sesion")
    fun getAll(): List<SesionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sesion: SesionEntity)

    @Delete
    fun delete(sesion: SesionEntity)
}