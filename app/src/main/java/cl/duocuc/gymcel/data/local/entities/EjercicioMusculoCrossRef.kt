package cl.duocuc.gymcel.data.local.entities
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "ejercicio_musculo",
    primaryKeys = ["ejercicio_id", "musculo_id"],
    foreignKeys = [
        ForeignKey(
            entity = EjercicioEntity::class,
            parentColumns = ["id"],
            childColumns = ["ejercicio_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MusculoEntity::class,
            parentColumns = ["id"],
            childColumns = ["musculo_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EjercicioMusculoCrossRef(
    val ejercicio_id: Int,
    val musculo_id: Int
)
