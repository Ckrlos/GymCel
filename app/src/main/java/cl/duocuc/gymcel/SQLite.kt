package cl.duocuc.gymcel

import android.content.Context
import androidx.room.Room
import cl.duocuc.gymcel.data.local.db.GymDatabase

object SQLite {

    @Volatile
    private var dbInstance: GymDatabase? = null

    fun getDatabase(context: Context): GymDatabase {
        return dbInstance ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                GymDatabase::class.java,
                "gymcel.db"
            )
                .fallbackToDestructiveMigration()
                .build()
            dbInstance = instance
            instance
        }
    }
    //TODO: si implementamos inicio de sesion aqui iria metodo cerrar sesion con token o id de sesion

}