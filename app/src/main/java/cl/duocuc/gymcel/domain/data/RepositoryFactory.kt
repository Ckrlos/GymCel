package cl.duocuc.gymcel.domain.data

import cl.duocuc.gymcel.data.local.dao.GymcelDao
import cl.duocuc.gymcel.data.repository.GymcelRepository

interface RepositoryFactory {

    fun <T, DAO : GymcelDao<T>> create(entityClass: Class<T>) : GymcelRepository<T, DAO>

    fun <T, DAO : GymcelDao<T>> create(dao: DAO) : GymcelRepository<T, DAO> = GymcelRepository(dao)

}