package cl.duocuc.gymcel.data.api.client

import cl.duocuc.gymcel.data.api.ExerciseDbApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class ExerciseDbApiClient private constructor() {

    companion object {
        private const val BASE_URL = "https://exercisedb-api.vercel.app/api/v1/"

        @Volatile
        private var instance: ExerciseDbApiService? = null

        fun getInstance(): ExerciseDbApiService {
            return instance ?: synchronized(this) {
                instance ?: buildApiClient().also { instance = it }
            }
        }

        private fun buildApiClient(): ExerciseDbApiService {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


            return retrofit.create()
            //return retrofit.create(ExerciseDbApiService::class.java)
        }
    }
}