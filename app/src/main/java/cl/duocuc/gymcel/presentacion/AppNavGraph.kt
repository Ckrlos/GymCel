package cl.duocuc.gymcel.presentacion

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cl.duocuc.gymcel.presentacion.ui.screens.HomeScreen
import cl.duocuc.gymcel.presentacion.ui.screens.RutinaDetalleScreen
import cl.duocuc.gymcel.presentacion.ui.screens.SeleccionarRutinaScreen



@Composable
fun AppNavGraph(navController: NavHostController, context: Context) {

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(navController = navController)
        }



        composable(
            route = "detalleRutina/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val rutinaId = backStackEntry.arguments?.getInt("id") ?: 0
            Text("Detalle de rutina $rutinaId (en construcción)")
        }

        composable("seleccionarRutina") {
            SeleccionarRutinaScreen(navController)
        }

        composable(
            "rutina_detalle/{rutinaId}",
            arguments = listOf(navArgument("rutinaId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val rutinaId = navBackStackEntry.arguments?.getLong("rutinaId")
            RutinaDetalleScreen(
                navController = navController,
                rutinaId = rutinaId
            )
        }
    }
}

