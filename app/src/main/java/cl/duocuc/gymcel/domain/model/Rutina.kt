package cl.duocuc.gymcel.domain.model

data class Rutina(
    val id: Int,
    val nombre: String,
    val descripcion: String?,
    val dia: String? = null
)
