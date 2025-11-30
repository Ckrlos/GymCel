package cl.duocuc.gymcel.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import cl.duocuc.gymcel.data.local.entities.TreinoEntity

@Dao
interface TreinoDao : BaseDao<TreinoEntity> {

    @Query("SELECT * FROM gmc_treino WHERE id = :id")
    suspend fun getById(id: Long): TreinoEntity?

    @Query("SELECT * FROM gmc_treino")
    suspend fun getAll(): List<TreinoEntity>

    @Query("DELETE FROM gmc_treino WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Query("SELECT COUNT(*) FROM gmc_treino")
    suspend fun count(): Int
}