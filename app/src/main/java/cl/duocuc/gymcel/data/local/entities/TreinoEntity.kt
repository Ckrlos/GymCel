package cl.duocuc.gymcel.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "gmc_treino",
    foreignKeys = [
        ForeignKey(
            entity = RutinaEntity::class,
            parentColumns = ["id"],
            childColumns = ["rutina_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class TreinoEntity (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val rutina_id: Long,
    val timestamp: Long = Instant.now().epochSecond,
    val done: Boolean,
    val notas: String
)