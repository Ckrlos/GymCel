package cl.duocuc.gymcel.data.local.entities

import androidx.room.*

@Entity(
    tableName = "sesion",
    foreignKeys = [
        ForeignKey(
            entity = RutinaEntity::class,
            parentColumns = ["id"],
            childColumns = ["rutina_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("rutina_id")]
)
data class SesionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val rutina_id: Int?,
    val date: String = "",
    val notas: String?
)