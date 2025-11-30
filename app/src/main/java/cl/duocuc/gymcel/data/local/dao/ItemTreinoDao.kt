package cl.duocuc.gymcel.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import cl.duocuc.gymcel.data.local.entities.ItemTreinoEntity

@Dao
interface ItemTreinoDao : GymcellDao<ItemTreinoEntity> {

    @Query("SELECT * FROM gmc_itemtreino WHERE id = :id")
    override suspend fun getById(id: Long): ItemTreinoEntity?

    @Query("SELECT * FROM gmc_itemtreino")
    override suspend fun getAll(): List<ItemTreinoEntity>

    @Query("DELETE FROM gmc_itemtreino WHERE id = :id")
    override suspend fun deleteById(id: Long): Int

    @Query("SELECT COUNT(*) FROM gmc_itemtreino")
    override suspend fun count(): Int
}