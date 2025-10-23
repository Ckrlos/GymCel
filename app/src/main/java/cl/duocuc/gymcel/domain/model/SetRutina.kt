package cl.duocuc.gymcel.domain.model

data class SetRutina(
    val id: Int,
    val rutinaEntryId: Int,
    val codigo: String?,
    val repeticiones: Int?,
    val rangoMin: Int?,
    val rangoMax: Int?,
    val carga: Double?,
    val unidadCarga: String,
    val descansoSegundos: Int?,
    val notas: String?
)
