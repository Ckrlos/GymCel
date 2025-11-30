package cl.duocuc.gymcel.domain.data

import androidx.room.withTransaction
import cl.duocuc.gymcel.data.local.dao.BaseDao
import cl.duocuc.gymcel.data.local.db.GymDatabase

abstract class DataService<DAO : BaseDao<*>>(
    protected val db: GymDatabase,
    protected val dao: DAO
) {

    suspend fun <R> withDao(block: suspend DAO.() -> R) = dao.block()
    suspend fun <R> transactional(block: suspend GymDatabase.() -> R) =
        db.withTransaction { db.block() }

}