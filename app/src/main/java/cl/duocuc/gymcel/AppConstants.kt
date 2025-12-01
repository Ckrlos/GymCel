package cl.duocuc.gymcel

import android.content.Context
import androidx.room.Room
import cl.duocuc.gymcel.data.api.ExerciseDbApiService
import cl.duocuc.gymcel.data.local.db.GymDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

object AppConstants {

    @Volatile
    private var dbInstance: GymDatabase? = null

    fun getDatabase(context: Context): GymDatabase {
        return dbInstance ?: synchronized(this) {
            dbInstance ?: Room.databaseBuilder(
                context.applicationContext,
                GymDatabase::class.java,
                "gymcel.db"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also { dbInstance = it }
        }
    }


    @Volatile
    private var apiServiceInstance: ExerciseDbApiService? = null
    private const val EXERCISEDB_BASE_URL = "https://www.exercisedb.dev/api/v1/"

    fun getApiService(): ExerciseDbApiService {
        return apiServiceInstance ?: synchronized(this) {
            apiServiceInstance ?: createApiClient().also { apiServiceInstance = it }
        }
    }

    private fun createApiClient(): ExerciseDbApiService {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(EXERCISEDB_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ExerciseDbApiService::class.java)
    }

}