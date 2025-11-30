package cl.duocuc.gymcel.data.local.dao

interface DaoFactory {

    fun <T : BaseDao<*, *>> create(entityClass: Class<*>): T

}