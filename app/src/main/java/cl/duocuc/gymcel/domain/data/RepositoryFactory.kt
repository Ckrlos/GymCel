package cl.duocuc.gymcel.domain.data

import cl.duocuc.gymcel.data.local.dao.GymcellDao
import cl.duocuc.gymcel.data.repository.GymCellRepository

interface RepositoryFactory {

    fun <T, DAO : GymcellDao<T>> create(entityClass: Class<T>) : GymCellRepository<T, DAO>

    fun <T, DAO : GymcellDao<T>> create(dao: DAO) : GymCellRepository<T, DAO> = GymCellRepository(dao)

}