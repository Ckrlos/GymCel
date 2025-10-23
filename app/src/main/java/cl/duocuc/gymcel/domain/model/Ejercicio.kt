package cl.duocuc.gymcel.domain.model

data class Ejercicio(
    val id: Int,
    val nombre: String,
    val descripcion: String?,
    val imagen: String?,
    val tipo: String?,
    val equipo: String?,
    val dificultad: Int?
)
