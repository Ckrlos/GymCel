package cl.duocuc.gymcel.data.repository

import cl.duocuc.gymcel.data.local.dao.RutinaDao
import cl.duocuc.gymcel.data.local.entities.RutinaEntity

class RutinaRepository(
    private val rutinaDao: RutinaDao
) : GymCellRepository<RutinaEntity, RutinaDao>(rutinaDao) {

    override suspend fun getById(id: Long) = rutinaDao.getById(id)

    override suspend fun getAll() = rutinaDao.getAll()

    override suspend fun deleteById(id: Long) {
        rutinaDao.deleteById(id)
    }

}