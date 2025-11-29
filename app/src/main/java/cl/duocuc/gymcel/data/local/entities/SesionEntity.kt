package cl.duocuc.gymcel.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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
    val date: String? = ZonedDateTime
        .now()
        .format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
    val notas: String?
)