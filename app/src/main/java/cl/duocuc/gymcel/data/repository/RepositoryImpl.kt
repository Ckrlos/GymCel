package cl.duocuc.gymcel.data.repository

import cl.duocuc.gymcel.data.local.dao.BaseDao
import cl.duocuc.gymcel.data.local.dao.GymcellDao
import cl.duocuc.gymcel.domain.data.Repository

open class BaseRepository<T, ID, DAO : BaseDao<T, ID>> (
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

    override suspend fun getById(id: ID): T? = dao.getById(id)

    override suspend fun getAll(): List<T> = dao.getAll()

    override suspend fun deleteById(id: ID) {
        dao.deleteById(id)
    }
}

class GymCellRepository<T, DAO : GymcellDao<T>>(
    dao: DAO
) : BaseRepository<T, Long, DAO>(dao)

