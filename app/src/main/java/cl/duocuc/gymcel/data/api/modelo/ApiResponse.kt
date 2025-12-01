package cl.duocuc.gymcel.data.api.modelo

import java.net.URL

open class ApiResponse<T>(
    val success: Boolean,
    val metadata: ApiMetaData?,
    val data: T
)

data class ApiMetaData(
    val totalExercises: Int,
    val totalPages: Int,
    val currentPage: Int,
    val previousPage: URL?,
    val nextPage: URL?,
)