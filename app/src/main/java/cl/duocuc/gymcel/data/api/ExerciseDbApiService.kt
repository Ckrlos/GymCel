package cl.duocuc.gymcel.data.api

import cl.duocuc.gymcel.data.api.modelo.ApiResponse
import cl.duocuc.gymcel.data.api.modelo.Exercise
import cl.duocuc.gymcel.data.api.modelo.ObjetoNombrado
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//FIXME: API ROTA CHATGPT SE INVENTO CUALQUIERA, REVISAR API EN PROFUNDIDAD...
interface ExerciseDbApiService {

    @GET("exercises")
    suspend fun getAllExercises(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Response<ApiResponse<List<Exercise>>>

    @GET("exercises/bodyPart/{bodyPart}")
    suspend fun getExercisesByBodyPart(
        @Path("bodyPart") bodyPart: String,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Response<ApiResponse<List<Exercise>>>

    @GET("exercises/target/{targetMuscle}")
    suspend fun getExercisesByTargetMuscle(
        @Path("targetMuscle") targetMuscle: String,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Response<ApiResponse<List<Exercise>>>

    @GET("exercises/equipment/{equipment}")
    suspend fun getExercisesByEquipment(
        @Path("equipment") equipment: String,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Response<ApiResponse<List<Exercise>>>

    @GET("exercises/{exerciseId}")
    suspend fun getExerciseById(
        @Path("exerciseId") exerciseId: String
    ): Response<ApiResponse<Exercise>>

    @GET("exercises/name/{name}")
    suspend fun getExercisesByName(
        @Path("name") name: String,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Response<ApiResponse<List<Exercise>>>

    @GET("bodyparts")
    suspend fun getBodyParts(): Response<ApiResponse<List<ObjetoNombrado>>>

    @GET("targets")
    suspend fun getTargetMuscles(): Response<ApiResponse<List<ObjetoNombrado>>>

    @GET("equipments")
    suspend fun getEquipments(): Response<ApiResponse<List<ObjetoNombrado>>>
}