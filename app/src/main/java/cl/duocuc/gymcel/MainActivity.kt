package cl.duocuc.gymcel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import cl.duocuc.gymcel.data.FactoryProvider
import cl.duocuc.gymcel.data.local.entities.RutinaEntity
import cl.duocuc.gymcel.presentacion.AppNavGraph
import cl.duocuc.gymcel.presentacion.ui.theme.GymTheme
import kotlinx.coroutines.runBlocking
import java.time.DayOfWeek


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        val db = AppConstants.getDatabase(this)
        val registro = FactoryProvider.registry(db)
        val rutina = RutinaEntity(
            1,
            "Rutina de ejemplo",
            "Descripción de la rutina de ejemplo",
            DayOfWeek.MONDAY.name
        )

        runBlocking {
            FactoryProvider.repositoryFactory(registro)
                .create(RutinaEntity::class.java)
                .save(rutina)
        }
         */
        setContent {
            GymTheme {
                Surface {
                    val navController = rememberNavController()
                    AppNavGraph(navController = navController, context = this)
                }
            }
        }
    }
}