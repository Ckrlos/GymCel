package cl.duocuc.gymcel.data.local.dao

interface BaseDao<T, ID> {

    suspend fun insert(entity: T): Long

    suspend fun insertAll(entities: List<T>): List<Long>

    suspend fun update(entity: T): Int

    suspend fun delete(entity: T): Int

    suspend fun getById(id: ID): T?

    suspend fun getAll(): List<T>

    suspend fun deleteById(id: ID): Int

    suspend fun count(): Int

}

interface GymcellDao<T> : BaseDao<T, Long>