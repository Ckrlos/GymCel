package cl.duocuc.gymcel.data.repository

import cl.duocuc.gymcel.data.local.dao.BaseDao
import cl.duocuc.gymcel.data.local.dao.GymcellDao
import cl.duocuc.gymcel.domain.data.RepositoryFactory

class RepositoryFactoryFunctionalImpl(
    private val daoMapper: (Class<*>) -> BaseDao<*, *>?
) : RepositoryFactory {

    @Suppress("UNCHECKED_CAST")
    override fun <T, DAO : GymcellDao<T>> create(entityClass: Class<T>): GymCellRepository<T, DAO> {
        val dao = daoMapper(entityClass)
            ?: throw IllegalArgumentException("No DAO for ${entityClass.simpleName}")

        return create(dao as DAO)
    }

}