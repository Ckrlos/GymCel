package cl.duocuc.gymcel.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rutina")
data class RutinaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val desc: String?
)