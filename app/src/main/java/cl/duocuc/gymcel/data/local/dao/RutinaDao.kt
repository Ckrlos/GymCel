package cl.duocuc.gymcel.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cl.duocuc.gymcel.data.local.entities.RutinaEntity


@Dao
interface RutinaDao : GymcellDao<RutinaEntity> {

    @Insert
    override suspend fun insert(entity: RutinaEntity): Long

    @Insert
    override suspend fun insertAll(entities: List<RutinaEntity>): List<Long>

    @Update
    override suspend fun update(entity: RutinaEntity): Int

    @Delete
    override suspend fun delete(entity: RutinaEntity): Int

    @Query("SELECT * FROM gmc_rutina WHERE id = :id")
    override suspend fun getById(id: Long): RutinaEntity?

    @Query("SELECT * FROM gmc_rutina")
    override suspend fun getAll(): List<RutinaEntity>

    @Query("DELETE FROM gmc_rutina WHERE id = :id")
    override suspend fun deleteById(id: Long): Int

    @Query("SELECT COUNT(*) FROM gmc_rutina")
    override suspend fun count(): Int


}
