package cl.duocuc.gymcel.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import cl.duocuc.gymcel.data.local.entities.ItemRutinaEntity

@Dao
interface ItemRutinaDao : GymcellDao<ItemRutinaEntity> {

    @Query("SELECT * FROM gmc_itemrutina WHERE id = :id")
    override suspend fun getById(id: Long): ItemRutinaEntity?

    @Query("SELECT * FROM gmc_itemrutina")
    override suspend fun getAll(): List<ItemRutinaEntity>

    @Query("DELETE FROM gmc_itemrutina WHERE id = :id")
    override suspend fun deleteById(id: Long): Int

    @Query("SELECT COUNT(*) FROM gmc_itemrutina")
    override suspend fun count(): Int
    
}