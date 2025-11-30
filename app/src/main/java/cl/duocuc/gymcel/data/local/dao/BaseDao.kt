package cl.duocuc.gymcel.data.local.dao

abstract class BaseDao<T, ID> {

    open suspend fun insert(entity: T): Long =
        throw UnsupportedOperationException()

    open suspend fun insertAll(entities: List<@JvmSuppressWildcards T>): List<Long> =
        throw UnsupportedOperationException()

    open suspend fun update(entity: T): Int =
        throw UnsupportedOperationException()

    open suspend fun delete(entity: T): Int =
        throw UnsupportedOperationException()

    open suspend fun getById(id: ID): T? =
        throw UnsupportedOperationException()

    open suspend fun getAll(): List<@JvmSuppressWildcards T> =
        throw UnsupportedOperationException()

    open suspend fun deleteById(id: ID): Int =
        throw UnsupportedOperationException()

    open suspend fun count(): Int =
        throw UnsupportedOperationException()
}


abstract class GymcellDao<T> : BaseDao<T, Long>()