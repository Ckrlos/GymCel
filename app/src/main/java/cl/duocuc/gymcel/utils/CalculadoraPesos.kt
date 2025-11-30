package cl.duocuc.gymcel.utils
object CalculadoraPesos {

    // Factor de conversión kg a lb
    private const val KG_TO_LB = 2.20462
    private const val LB_TO_KG = 0.453592

    /**
     * Convierte un peso de una unidad a otra
     * @param peso El peso a convertir
     * @param desdeUnidad Unidad original ("kg" o "lb")
     * @param haciaUnidad Unidad destino ("kg" o "lb")
     * @return Peso convertido
     */
    fun convertirPeso(peso: Double, desdeUnidad: String, haciaUnidad: String): Double {
        return when {
            desdeUnidad == haciaUnidad -> peso
            desdeUnidad == "kg" && haciaUnidad == "lb" -> peso * KG_TO_LB
            desdeUnidad == "lb" && haciaUnidad == "kg" -> peso * LB_TO_KG
            else -> peso // Por defecto, no convertir
        }
    }

    /**
     * Calcula el incremento sugerido basado en la unidad
     * @param unidad Unidad actual ("kg" o "lb")
     * @return Incremento sugerido
     */
    fun obtenerIncrementoSugerido(unidad: String): Double {
        return when (unidad) {
            "kg" -> 2.5
            "lb" -> 5.0
            else -> 1.0
        }
    }

    /**
     * Formatea el peso para mostrar con decimales apropiados
     */
    fun formatearPeso(peso: Double, unidad: String): String {
        return if (peso > 0) {
            val decimales = if (unidad == "kg") 1 else 0
            "%.${decimales}f".format(peso)
        } else {
            "0"
        }
    }
}