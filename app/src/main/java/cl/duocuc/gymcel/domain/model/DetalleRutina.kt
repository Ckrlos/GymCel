package cl.duocuc.gymcel.domain.model

data class DetalleRutina(
    val id: Long,
    val ejercicio: Ejercicio,
    val orden: Int,
    val series: Int,
    val objetivoReps: Int?,
    val tipoSerie: TipoSerie? = TipoSerie.STRAIGHT,
    val rangoReps: IntRange?,
    val detalleTreino: DetalleTreino? = null
) {
    fun isRange(): Boolean = rangoReps != null && rangoReps.first < rangoReps.last
}