package cl.duocuc.gymcel.data.repository

import android.content.Context
import cl.duocuc.gymcel.Singleton
import cl.duocuc.gymcel.data.local.dao.MusculoDao
import cl.duocuc.gymcel.data.mapper.toDomain
import cl.duocuc.gymcel.data.mapper.toEntity
import cl.duocuc.gymcel.domain.model.Musculo
import cl.duocuc.gymcel.domain.repository.MusculoRepository

class MusculoRepositoryImpl(context: Context) : MusculoRepository{
    private val dao: MusculoDao = Singleton.getDatabase(context).musculoDao()

    override fun obtenerMusculos(): List<Musculo> {
        // 💡 AQUÍ se usa el mapper para pasar de Entity → Domain
        return dao.getAll().map { it.toDomain() }
    }

    override fun guardarMusculo(musculo: Musculo) {
        // 💡 AQUÍ se usa el mapper para pasar de Domain → Entity
        dao.insert(musculo.toEntity())
    }
}