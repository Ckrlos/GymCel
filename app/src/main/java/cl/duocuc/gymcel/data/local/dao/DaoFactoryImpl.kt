package cl.duocuc.gymcel.data.local.dao


class DaoFactoryImpl(
    private val registry: Map<Class<*>, BaseDao<*, *>>
) : DaoFactory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : BaseDao<*, *>> create(entityClass: Class<*>): T {
        return registry[entityClass] as? T
            ?: throw IllegalArgumentException("No DAO for ${entityClass.simpleName}")
    }



}