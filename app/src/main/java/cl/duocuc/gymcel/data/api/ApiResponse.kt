package cl.duocuc.gymcel.data.api

import java.net.URL

open class ApiResponse<T>(
    val success: Boolean,
    val metadata: ApiMetaData?,
    val data: T
)

open class ListApiResponse<E>(
    success: Boolean,
    metadata: ApiMetaData?,
    data: List<E>
) : ApiResponse<List<E>>(success, metadata, data)

data class ApiMetaData(
    val totalExercises: Int,
    val totalPages: Int,
    val currentPage: Int,
    val previousPage: URL?,
    val nextPage: URL?,
)