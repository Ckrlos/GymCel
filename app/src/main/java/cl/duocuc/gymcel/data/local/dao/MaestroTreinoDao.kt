package cl.duocuc.gymcel.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cl.duocuc.gymcel.data.local.entities.relations.MaestroTreino

@Dao
abstract class MaestroTreinoDao: GymcelDao<MaestroTreino>() {

    @Insert
    abstract override suspend fun insert(entity: MaestroTreino): Long

    @Insert
    abstract override suspend fun insertAll(entities: List<MaestroTreino>): List<Long>

    @Update
    abstract override suspend fun update(entity: MaestroTreino): Int

    @Delete
    abstract override suspend fun delete(entity: MaestroTreino): Int

    @Query("SELECT * FROM gmc_treino WHERE id = :id")
    abstract override suspend fun getById(id: Long): MaestroTreino?

    @Query("SELECT * FROM gmc_treino")
    abstract override suspend fun getAll(): List<MaestroTreino>

    @Query("DELETE FROM gmc_treino WHERE id = :id")
    abstract override suspend fun deleteById(id: Long): Int

    @Query("SELECT COUNT(*) FROM gmc_treino")
    abstract override suspend fun count(): Int
}