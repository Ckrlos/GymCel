package cl.duocuc.gymcel.data.local.dao
import androidx.room.*
import cl.duocuc.gymcel.data.local.entities.MusculoEntity


@Dao
interface MusculoDao {
    @Query("SELECT * FROM musculo")
    fun getAll(): List< MusculoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(musculo: MusculoEntity)

    @Delete
    fun delete(musculo: MusculoEntity)
}