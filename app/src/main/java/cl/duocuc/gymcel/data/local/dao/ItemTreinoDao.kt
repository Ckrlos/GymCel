package cl.duocuc.gymcel.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cl.duocuc.gymcel.data.local.entities.ItemTreinoEntity

@Dao
interface ItemTreinoDao : GymcellDao<ItemTreinoEntity> {

    @Insert
    override suspend fun insert(entity: ItemTreinoEntity): Long

    @Insert
    override suspend fun insertAll(entities: List<ItemTreinoEntity>): List<Long>

    @Update
    override suspend fun update(entity: ItemTreinoEntity): Int

    @Delete
    override suspend fun delete(entity: ItemTreinoEntity): Int

    @Query("SELECT * FROM gmc_itemtreino WHERE id = :id")
    override suspend fun getById(id: Long): ItemTreinoEntity?

    @Query("SELECT * FROM gmc_itemtreino")
    override suspend fun getAll(): List<ItemTreinoEntity>

    @Query("DELETE FROM gmc_itemtreino WHERE id = :id")
    override suspend fun deleteById(id: Long): Int

    @Query("SELECT COUNT(*) FROM gmc_itemtreino")
    override suspend fun count(): Int
}