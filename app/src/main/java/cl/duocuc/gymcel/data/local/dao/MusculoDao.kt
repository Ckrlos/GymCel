package cl.duocuc.gymcel.data.local.dao
import androidx.room.*
import cl.duocuc.gymcel.data.local.entities.MusculoEntity


@Dao
interface MusculoDao {
    @Query("SELECT * FROM musculo")
    suspend fun getAll(): List< MusculoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(musculo: MusculoEntity)

    @Delete
    suspend fun delete(musculo: MusculoEntity)
}