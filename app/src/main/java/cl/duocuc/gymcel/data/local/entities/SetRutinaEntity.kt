package cl.duocuc.gymcel.data.local.entities

import androidx.room.*

@Entity(
    tableName = "set_rutina",
    foreignKeys = [
        ForeignKey(
            entity = RutinaEntryEntity::class,
            parentColumns = ["id"],
            childColumns = ["rutina_entry_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("rutina_entry_id")]
)
data class SetRutinaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val rutina_entry_id: Int,
    val code: String?,
    val reps: Int?,
    val rep_range_min: Int?,
    val rep_range_max: Int?,
    val load: Double?,
    val load_unit: String = "KG",
    val rest_time_sec: Int?,
    val notas: String?
)