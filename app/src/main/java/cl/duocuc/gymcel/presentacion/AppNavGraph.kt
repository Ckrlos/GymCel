package cl.duocuc.gymcel.presentacion

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cl.duocuc.gymcel.AppConstants
import cl.duocuc.gymcel.presentacion.factory.SeleccionarRutinaViewModelFactory
import cl.duocuc.gymcel.presentacion.ui.screens.HomeScreen
import cl.duocuc.gymcel.presentacion.ui.screens.RutinaDetalleScreen
import cl.duocuc.gymcel.presentacion.ui.screens.SeleccionarRutinaScreen
import cl.duocuc.gymcel.presentacion.viewmodel.SeleccionarRutinaViewModel


@Composable
fun AppNavGraph(navController: NavHostController, context: Context) {
    val db = AppConstants.getDatabase(context)
    val viewModel: SeleccionarRutinaViewModel = viewModel(
        factory = SeleccionarRutinaViewModelFactory(db)
    )

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
            val db = AppConstants.getDatabase(context)
            val viewModel: SeleccionarRutinaViewModel = viewModel(
                factory = SeleccionarRutinaViewModelFactory(db)
            )
            SeleccionarRutinaScreen(navController, viewModel)
        }


        // En AppNavGraph.kt - actualizar la ruta
        composable(
            "rutina_detalle/{rutinaId}",
            arguments = listOf(navArgument("rutinaId") { type = NavType.LongType })
        ) { navBackStackEntry ->
            val rutinaId = navBackStackEntry.arguments?.getLong("rutinaId") ?: 0L // valor por defecto
            RutinaDetalleScreen(
                navController = navController,
                rutinaId = rutinaId
            )
        }


    }
}

