package cl.duocuc.gymcel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import cl.duocuc.gymcel.presentacion.AppNavGraph
import cl.duocuc.gymcel.presentacion.ui.theme.GymTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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