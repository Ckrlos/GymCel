package cl.duocuc.gymcel.data


import cl.duocuc.gymcel.data.local.dao.DaoFactory
import cl.duocuc.gymcel.data.local.dao.DaoFactoryImpl
import cl.duocuc.gymcel.data.local.dao.GymcelDao
import cl.duocuc.gymcel.data.local.db.GymDatabase
import cl.duocuc.gymcel.data.local.entities.*
import cl.duocuc.gymcel.data.repository.RepositoryFactoryFunctionalImpl
import cl.duocuc.gymcel.data.repository.RepositoryFactoryMappedImpl
import cl.duocuc.gymcel.domain.data.RepositoryFactory

object FactoryProvider {

    // Nunca nulo. Si no ha sido inicializado, se lanza error explícito.
    private lateinit var cache: Map<Class<*>, GymcelDao<*>>

    /** Inicializa el registro y lo retorna. */
    fun registry(db: GymDatabase): Map<Class<*>, GymcelDao<*>> {
        if (!::cache.isInitialized) {
            cache = mapOf(
                RutinaEntity::class.java to db.rutinaDao(),
                ItemRutinaEntity::class.java to db.itemRutinaDao(),
                TreinoEntity::class.java to db.treinoDao(),
                ItemTreinoEntity::class.java to db.itemTreinoDao(),
            )
        }
        return cache
    }

    /** Obtiene el DaoFactory con el registro ya inicializado. */
    fun daoFactory(): DaoFactory {
        ensureRegistry()
        return daoFactory(cache)
    }

    fun daoFactory(registry: Map<Class<*>, GymcelDao<*>>): DaoFactory =
        DaoFactoryImpl(registry)

    fun repositoryFactory(): RepositoryFactory {
        ensureRegistry()
        return repositoryFactory(cache)
    }

    /** RepositoryFactory basado en un mapa directo */
    fun repositoryFactory(registry: Map<Class<*>, GymcelDao<*>>): RepositoryFactory =
        RepositoryFactoryMappedImpl(registry)

    /** RepositoryFactory basado en un DaoFactory */
    fun repositoryFactory(daoFactory: DaoFactory): RepositoryFactory =
        repositoryFactory(daoFactory::create)

    /** RepositoryFactory basado en una lambda mapper */
    fun repositoryFactory(daoMapper: (Class<*>) -> GymcelDao<*>?): RepositoryFactory =
        RepositoryFactoryFunctionalImpl(daoMapper)

    /** Falla temprano si alguien intenta usar factories sin registro */
    private fun ensureRegistry() {
        if (!::cache.isInitialized) {
            throw IllegalStateException(
                "FactoryProvider: registry(db) debe ser llamado antes de solicitar un DaoFactory o RepositoryFactory."
            )
        }
    }

}
