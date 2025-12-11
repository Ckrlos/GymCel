package cl.duocuc.gymcel.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cl.duocuc.gymcel.data.local.entities.relations.MaestroRutina

@Dao
abstract class MaestroRutinaDao: GymcelDao<MaestroRutina>() {

    @Insert
    abstract override suspend fun insert(entity: MaestroRutina): Long

    @Insert
    abstract override suspend fun insertAll(entities: List<@JvmSuppressWildcards MaestroRutina>): List<Long>

    @Update
    abstract override suspend fun update(entity: MaestroRutina): Int

    @Delete
    abstract override suspend fun delete(entity: MaestroRutina): Int

    @Query("SELECT * FROM gmc_rutina WHERE id = :id")
    abstract override suspend fun getById(id: Long): MaestroRutina?

    @Query("SELECT * FROM gmc_rutina")
    abstract override suspend fun getAll(): List<@JvmSuppressWildcards MaestroRutina>

    @Query("DELETE FROM gmc_rutina WHERE id = :id")
    abstract override suspend fun deleteById(id: Long): Int

    @Query("SELECT COUNT(*) FROM gmc_rutina")
    abstract override suspend fun count(): Int

}