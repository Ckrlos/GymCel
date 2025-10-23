package cl.duocuc.gymcel.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ejercicio")
data class EjercicioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val desc: String?,
    val img: String?,
    val type: String?,
    val equipment: String?,
    val difficulty: Int?
)