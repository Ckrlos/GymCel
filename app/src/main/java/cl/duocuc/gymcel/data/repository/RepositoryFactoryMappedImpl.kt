package cl.duocuc.gymcel.data.repository

import cl.duocuc.gymcel.data.local.dao.BaseDao
import cl.duocuc.gymcel.data.local.dao.GymcellDao
import cl.duocuc.gymcel.domain.data.RepositoryFactory

class RepositoryFactoryMappedImpl (
    private val registry: Map<Class<*>, BaseDao<*, *>>
) : RepositoryFactory {

    @Suppress("UNCHECKED_CAST")
    override fun <T, DAO : GymcellDao<T>> create(entityClass: Class<T>): GymCellRepository<T, DAO> {
        val dao = registry[entityClass]
            ?: IllegalArgumentException("No DAO registrado para ${entityClass.simpleName}")

        return create(dao as DAO)
    }

}