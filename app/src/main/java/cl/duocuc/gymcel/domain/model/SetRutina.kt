package cl.duocuc.gymcel.domain.model

data class SetRutina(
    val id: Int = 0,
    val rutinaEntryId: Int,
    val codigo: Int = 0,
    val repeticiones: Int,
    val rangoMin: Int = 0,
    val rangoMax: Int = 0,
    val carga: Double,
    val unidadCarga: String,
    val descansoSegundos: Int,
    val notas: String? = null
)