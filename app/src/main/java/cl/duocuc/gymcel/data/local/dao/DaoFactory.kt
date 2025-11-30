package cl.duocuc.gymcel.data.local.dao

import cl.duocuc.gymcel.data.local.db.GymDatabase
import cl.duocuc.gymcel.data.local.entities.ItemRutinaEntity
import cl.duocuc.gymcel.data.local.entities.ItemTreinoEntity
import cl.duocuc.gymcel.data.local.entities.RutinaEntity
import cl.duocuc.gymcel.data.local.entities.TreinoEntity

class DaoFactory(val db: GymDatabase) {
    private val daoMap: Map<Class<*>, BaseDao<*>> = mapOf(
        RutinaEntity::class.java to db.rutinaDao(),
        ItemRutinaEntity::class.java to db.itemRutinaDao(),
        TreinoEntity::class.java to db.treinoDao(),
        ItemTreinoEntity::class.java to db.itemTreinoDao(),
    )

    @Suppress("UNCHECKED_CAST")
    fun <T : BaseDao<*>> getDao(entityClass: Class<*>): T {
        return daoMap[entityClass] as? T
            ?: throw IllegalArgumentException("No DAO for ${entityClass.simpleName}")
    }

}