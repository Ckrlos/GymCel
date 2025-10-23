package cl.duocuc.gymcel.data.local.entities

import androidx.room.*

@Entity(
    tableName = "rutina_entry",
    foreignKeys = [
        ForeignKey(
            entity = RutinaEntity::class,
            parentColumns = ["id"],
            childColumns = ["rutina_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EjercicioEntity::class,
            parentColumns = ["id"],
            childColumns = ["ejercicio_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("rutina_id"), Index("ejercicio_id")]
)
data class RutinaEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val rutina_id: Int,
    val ejercicio_id: Int,
    val name: String?,
    val order_index: Int = 0,
    val sets_amount: Int?,
    val set_range_min: Int?,
    val set_range_max: Int?
)