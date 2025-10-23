package cl.duocuc.gymcel.data.local.db
import androidx.room.Database
import androidx.room.RoomDatabase
import cl.duocuc.gymcel.data.local.dao.EjercicioDao
import cl.duocuc.gymcel.data.local.dao.MusculoDao
import cl.duocuc.gymcel.data.local.dao.RutinaDao
import cl.duocuc.gymcel.data.local.dao.SesionDao
import cl.duocuc.gymcel.data.local.entities.EjercicioEntity
import cl.duocuc.gymcel.data.local.entities.EjercicioMusculoCrossRef
import cl.duocuc.gymcel.data.local.entities.MusculoEntity
import cl.duocuc.gymcel.data.local.entities.RutinaEntity
import cl.duocuc.gymcel.data.local.entities.RutinaEntryEntity
import cl.duocuc.gymcel.data.local.entities.SesionDetalleEntity
import cl.duocuc.gymcel.data.local.entities.SesionEntity
import cl.duocuc.gymcel.data.local.entities.SetRutinaEntity

@Database(
    entities = [
        MusculoEntity::class,
        EjercicioEntity::class,
        EjercicioMusculoCrossRef::class,
        RutinaEntity::class,
        RutinaEntryEntity::class,
        SetRutinaEntity::class,
        SesionEntity::class,
        SesionDetalleEntity::class
    ],
    version = 1
)
abstract class GymDatabase : RoomDatabase() {
    abstract fun musculoDao(): MusculoDao
    abstract fun ejercicioDao(): EjercicioDao
    abstract fun rutinaDao(): RutinaDao
    abstract fun sesionDao(): SesionDao
}
