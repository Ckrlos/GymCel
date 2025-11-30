package cl.duocuc.gymcel.data.local.entities

import androidx.room.*

@Entity(
    tableName = "gmc_itemtreino",
    foreignKeys = [
        ForeignKey(
            entity = TreinoEntity::class,
            parentColumns = ["id"],
            childColumns = ["treino_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("treino_id")]
)
data class ItemTreinoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val treino_id: Int,
    val index: Int = 0,
    val effective_reps: Int,
    val effective_load: Double,
    val load_unit: String,
    val rir: Int?,
    val rest_nanos: Long
){

}