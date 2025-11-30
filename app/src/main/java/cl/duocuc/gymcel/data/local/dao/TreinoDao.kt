package cl.duocuc.gymcel.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import cl.duocuc.gymcel.data.local.entities.TreinoEntity

@Dao
interface TreinoDao : GymcellDao<TreinoEntity> {

    @Query("SELECT * FROM gmc_treino WHERE id = :id")
    override suspend fun getById(id: Long): TreinoEntity?

    @Query("SELECT * FROM gmc_treino")
    override suspend fun getAll(): List<TreinoEntity>

    @Query("DELETE FROM gmc_treino WHERE id = :id")
    override suspend fun deleteById(id: Long): Int

    @Query("SELECT COUNT(*) FROM gmc_treino")
    override suspend fun count(): Int
}