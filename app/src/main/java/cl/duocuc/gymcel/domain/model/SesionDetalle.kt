package cl.duocuc.gymcel.domain.model

data class SesionDetalle(
    val id: Int,
    val sesionId: Int,
    val setRutinaId: Int,
    val linea: Int?,
    val repeticiones: Int?,
    val cargaEfectiva: Double?,
    val unidadCarga: String,
    val rir: Int?,
    val rpe: Int?,
    val descansoSegundos: Int?,
    val notas: String?
)
