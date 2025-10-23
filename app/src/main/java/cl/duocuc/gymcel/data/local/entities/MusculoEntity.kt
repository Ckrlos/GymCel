package cl.duocuc.gymcel.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "musculo")
data class MusculoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val desc: String?,
    val grupo: String
)