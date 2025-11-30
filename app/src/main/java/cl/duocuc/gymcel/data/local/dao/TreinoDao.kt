package cl.duocuc.gymcel.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cl.duocuc.gymcel.data.local.entities.TreinoEntity

@Dao
interface TreinoDao : GymcellDao<TreinoEntity> {


    @Insert
    override suspend fun insert(entity: TreinoEntity): Long

    @Insert
    override suspend fun insertAll(entities: List<TreinoEntity>): List<Long>

    @Update
    override suspend fun update(entity: TreinoEntity): Int

    @Delete
    override suspend fun delete(entity: TreinoEntity): Int

    @Query("SELECT * FROM gmc_treino WHERE id = :id")
    override suspend fun getById(id: Long): TreinoEntity?

    @Query("SELECT * FROM gmc_treino")
    override suspend fun getAll(): List<TreinoEntity>

    @Query("DELETE FROM gmc_treino WHERE id = :id")
    override suspend fun deleteById(id: Long): Int

    @Query("SELECT COUNT(*) FROM gmc_treino")
    override suspend fun count(): Int
}