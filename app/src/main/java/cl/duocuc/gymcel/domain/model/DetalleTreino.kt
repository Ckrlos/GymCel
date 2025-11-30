package cl.duocuc.gymcel.domain.model

import java.time.Duration

data class DetalleTreino(
    val id: Long,
    val indice: Int,
    val repeticionesEfectivas: Int,
    val cargaEfectiva: Peso,
    val rir: Int?,
    val descanso: Duration? = null
)