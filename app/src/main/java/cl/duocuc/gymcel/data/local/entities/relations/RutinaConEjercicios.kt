package cl.duocuc.gymcel.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import cl.duocuc.gymcel.data.local.entities.*

data class RutinaConEjercicios(
    @Embedded val rutina: RutinaEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "rutina_id",
        entity = RutinaEntryEntity::class
    )
    val entries: List<RutinaEntryConEjercicioYSets>
)

data class RutinaEntryConEjercicioYSets(
    @Embedded val entry: RutinaEntryEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "rutina_entry_id"
    )
    val sets: List<SetRutinaEntity>,

    @Relation(
        parentColumn = "ejercicio_id",
        entityColumn = "id",
        entity = EjercicioEntity::class
    )
    val ejercicio: EjercicioConMusculos
)
data class EjercicioConMusculos(
    @Embedded val ejercicio: EjercicioEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            EjercicioMusculoCrossRef::class,
            parentColumn = "ejercicio_id",
            entityColumn = "musculo_id"
        )
    )
    val musculos: List<MusculoEntity>
)