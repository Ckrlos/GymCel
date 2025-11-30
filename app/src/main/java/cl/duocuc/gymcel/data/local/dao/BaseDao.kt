package cl.duocuc.gymcel.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface BaseDao<T, ID> {

    @Insert
    suspend fun insert(entity: T): Long

    @Insert
    suspend fun insertAll(entities: List<T>): List<Long>

    @Update
    suspend fun update(entity: T): Int

    @Delete
    suspend fun delete(entity: T): Int

    suspend fun getById(id: ID): T?

    suspend fun getAll(): List<T>

    suspend fun deleteById(id: ID): Int

    suspend fun count(): Int

}

interface GymcellDao<T> : BaseDao<T, Long>