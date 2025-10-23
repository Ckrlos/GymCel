package cl.duocuc.gymcel.domain.model

data class RutinaEntry(
    val id: Int,
    val rutinaId: Int,
    val ejercicioId: Int,
    val nombre: String?,
    val orden: Int = 0,
    val cantidadSets: Int?,
    val rangoMin: Int?,
    val rangoMax: Int?
)
