package cl.duocuc.gymcel.data.local.entities

import androidx.room.*

@Entity(
    tableName = "sesion_detalle",
    foreignKeys = [
        ForeignKey(
            entity = SesionEntity::class,
            parentColumns = ["id"],
            childColumns = ["sesion_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SetRutinaEntity::class,
            parentColumns = ["id"],
            childColumns = ["set_rutina_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("sesion_id"), Index("set_rutina_id")]
)
data class SesionDetalleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sesion_id: Int,
    val set_rutina_id: Int,
    val linea: Int?,
    val effective_reps: Int?,
    val effective_load: Double?,
    val load_unit: String = "KG",
    val rir: Int?,
    val rpe: Int?,
    val rest_time_sec: Int?,
    val notas: String?
)