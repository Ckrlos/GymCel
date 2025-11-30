package cl.duocuc.gymcel.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cl.duocuc.gymcel.data.local.entities.ItemRutinaEntity

@Dao
interface ItemRutinaDao : GymcellDao<ItemRutinaEntity> {

    @Insert
    override suspend fun insert(entity: ItemRutinaEntity): Long

    @Insert
    override suspend fun insertAll(entities: List<ItemRutinaEntity>): List<Long>

    @Update
    override suspend fun update(entity: ItemRutinaEntity): Int

    @Delete
    override suspend fun delete(entity: ItemRutinaEntity): Int

    @Query("SELECT * FROM gmc_itemrutina WHERE id = :id")
    override suspend fun getById(id: Long): ItemRutinaEntity?

    @Query("SELECT * FROM gmc_itemrutina")
    override suspend fun getAll(): List<ItemRutinaEntity>

    @Query("DELETE FROM gmc_itemrutina WHERE id = :id")
    override suspend fun deleteById(id: Long): Int

    @Query("SELECT COUNT(*) FROM gmc_itemrutina")
    override suspend fun count(): Int
    
}