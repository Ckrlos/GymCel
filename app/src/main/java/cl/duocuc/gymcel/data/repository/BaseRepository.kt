package cl.duocuc.gymcel.data.repository

import cl.duocuc.gymcel.data.local.dao.BaseDao
import cl.duocuc.gymcel.domain.data.Repository

abstract class BaseRepository<T, ID, DAO : BaseDao<T>> (
    protected val dao: DAO
): Repository<T, ID> {

    override suspend fun save(entity: T) {
        dao.insert(entity)
    }

    override suspend fun saveAll(entity: List<T>) {
        dao.insertAll(entity)
    }

    override suspend fun update(entity: T) {
        dao.update(entity)
    }

    override suspend fun delete(entity: T) {
        dao.delete(entity)
    }
}

abstract class GymCellRepository<T, DAO : BaseDao<T>>(
    dao: DAO
) : BaseRepository<T, Long, DAO>(dao)
