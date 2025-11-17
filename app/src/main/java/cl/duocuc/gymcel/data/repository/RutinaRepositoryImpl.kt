package cl.duocuc.gymcel.data.repository

import android.content.Context
import androidx.room.withTransaction
import cl.duocuc.gymcel.SQLite
import cl.duocuc.gymcel.data.local.dao.EjercicioDao
import cl.duocuc.gymcel.data.local.dao.EjercicioMusculoDao
import cl.duocuc.gymcel.data.local.dao.RutinaDao
import cl.duocuc.gymcel.data.local.dao.RutinaEntryDao
import cl.duocuc.gymcel.data.local.dao.SetRutinaDao
import cl.duocuc.gymcel.data.local.entities.*
import cl.duocuc.gymcel.data.mapper.toDomain
import cl.duocuc.gymcel.data.mapper.toEntity
import cl.duocuc.gymcel.domain.model.Ejercicio
import cl.duocuc.gymcel.domain.model.Musculo
import cl.duocuc.gymcel.domain.model.Rutina
import cl.duocuc.gymcel.domain.repository.RutinaRepository

class RutinaRepositoryImpl(
    context: Context
) : RutinaRepository {

    private val db = SQLite.getDatabase(context)
    private val rutinaDao: RutinaDao = SQLite.getDatabase(context).rutinaDao()
    private val ejercicioDao: EjercicioDao = SQLite.getDatabase(context).ejercicioDao()
    private val rutinaEntryDao: RutinaEntryDao = SQLite.getDatabase(context).rutinaEntryDao()
    private val setRutinaDao: SetRutinaDao = SQLite.getDatabase(context).setRutinaDao()
    private val ejercicioMusculoDao: EjercicioMusculoDao = SQLite.getDatabase(context).ejercicioMusculoDao()

    override suspend fun obtenerRutinas(): List<Rutina> {
        return rutinaDao.getAll().map { it.toDomain() }
    }

    override suspend fun guardarRutina(rutina: Rutina) {
        rutinaDao.insert(rutina.toEntity())
    }

    override suspend fun eliminarRutina(rutina: Rutina) {
        rutinaDao.delete(rutina.toEntity())
    }

    override suspend fun guardarRutinaYObtenerId(rutina: Rutina): Long {
        return rutinaDao.insertAndReturnId(rutina.toEntity())
    }

    override suspend fun guardarEjercicioCompleto(
        rutinaId: Int,
        ejercicio: Ejercicio,
        musculos: List<Musculo>,
        series: Int,
        reps: Int,
        carga: Double,
        descanso: Int
    ) {
        db.withTransaction {
            val ejercicioId = ejercicioDao.insertAndReturnId(ejercicio.toEntity()).toInt()

            val entryId = rutinaEntryDao.insertAndReturnId(
                RutinaEntryEntity(
                    rutina_id = rutinaId,
                    ejercicio_id = ejercicioId,
                    name = ejercicio.nombre,
                    sets_amount = series,
                    set_range_min = reps,
                    set_range_max = reps
                )
            ).toInt()

            repeat(series) {
                setRutinaDao.insert(
                    SetRutinaEntity(
                        rutina_entry_id = entryId,
                        reps = reps,
                        load = carga,
                        load_unit = "kg",
                        rest_time_sec = descanso
                    )
                )
            }

            musculos.forEach { musculo ->
                ejercicioMusculoDao.insert(
                    EjercicioMusculoCrossRef(
                        ejercicio_id = ejercicioId,
                        musculo_id = musculo.id
                    )
                )
            }
        }
    }
}
