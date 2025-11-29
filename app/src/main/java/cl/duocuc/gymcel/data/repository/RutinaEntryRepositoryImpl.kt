package cl.duocuc.gymcel.data.repository

import android.content.Context
import cl.duocuc.gymcel.AppConstants
import cl.duocuc.gymcel.data.local.entities.RutinaEntryEntity

class RutinaEntryRepositoryImpl(private val context: Context) {

    val dao = AppConstants.getDatabase(context).rutinaEntryDao()

    suspend fun insertar(entry: RutinaEntryEntity) {
        dao.insertEntry(entry)
    }

    suspend fun obtenerPorRutina(rutinaId: Int) =
        dao.getEntriesByRutinaId(rutinaId)
}
