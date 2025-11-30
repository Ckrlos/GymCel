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
import cl.duocuc.gymcel.domain.usecase.AgregarEjercicioARutinaUseCase
import cl.duocuc.gymcel.domain.usecase.GuardarEjercicioCompletoUseCase
import cl.duocuc.gymcel.domain.usecase.GuardarMusculoUseCase
import cl.duocuc.gymcel.domain.usecase.GuardarRutinaUseCase
import cl.duocuc.gymcel.domain.usecase.GuardarRutinaYObtenerIdUseCase
import cl.duocuc.gymcel.domain.usecase.ObtenerEjerciciosUseCase
import cl.duocuc.gymcel.domain.usecase.ObtenerMusculosUseCase
import cl.duocuc.gymcel.domain.usecase.ObtenerRutinasConEjerciciosUseCase
import cl.duocuc.gymcel.domain.usecase.ObtenerRutinasUseCase
import cl.duocuc.gymcel.presentacion.factory.RutinaViewModelFactory
import cl.duocuc.gymcel.presentacion.ui.screens.HomeScreen
import cl.duocuc.gymcel.presentacion.ui.screens.RutinaFormScreen
import cl.duocuc.gymcel.presentacion.viewmodel.RutinaViewModel


@Composable
fun AppNavGraph(navController: NavHostController, context: Context) {


    val musculoRepo = MusculoRepositoryImpl(context)
    val rutinaRepo = RutinaRepositoryImpl(context)
    val ejercicioRepo = EjercicioRepositoryImpl(context)
    val rutinaConEjerciciosRepo = RutinaConEjerciciosRepositoryImpl(context)


    val obtenerMusculosUseCase = ObtenerMusculosUseCase(musculoRepo)
    val guardarMusculoUseCase = GuardarMusculoUseCase(musculoRepo)

    val obtenerRutinasUseCase = ObtenerRutinasUseCase(rutinaRepo)
    val guardarRutinaUseCase = GuardarRutinaUseCase(rutinaRepo)
    val guardarRutinaYObtenerIdUseCase = GuardarRutinaYObtenerIdUseCase(rutinaRepo)
    val agregarEjercicioARutinaUseCase =
        AgregarEjercicioARutinaUseCase(AppConstants.getDatabase(context).rutinaEntryDao())
    val guardarEjercicioCompletoUseCase = GuardarEjercicioCompletoUseCase(rutinaRepo)

    val obtenerEjerciciosUseCase = ObtenerEjerciciosUseCase(ejercicioRepo)
    val obtenerRutinasConEjerciciosUseCase =
        ObtenerRutinasConEjerciciosUseCase(rutinaConEjerciciosRepo)

    val musculoViewModel: MusculoViewModel = viewModel(
        factory = MusculoViewModelFactory(obtenerMusculosUseCase, guardarMusculoUseCase)
    )

    val rutinaViewModel: RutinaViewModel = viewModel(
        factory = RutinaViewModelFactory(
            context,
            obtenerRutinasUseCase,
            guardarRutinaUseCase,
            guardarRutinaYObtenerIdUseCase,
            agregarEjercicioARutinaUseCase,
            guardarEjercicioCompletoUseCase
        )
    )

    val ejercicioViewModel: EjercicioViewModel = viewModel(
        factory = EjercicioViewModelFactory(obtenerEjerciciosUseCase)
    )

    val semanaViewModel: SemanaViewModel = viewModel(
        factory = SemanaViewModelFactory(obtenerRutinasConEjerciciosUseCase)
    )

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(navController = navController)
        }

        composable(
            route = "formRutina?dia={dia}",
            arguments = listOf(navArgument("dia") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) { backStackEntry ->
            val dia = backStackEntry.arguments?.getString("dia") ?: ""
            RutinaFormScreen(
                navController = navController,
                viewModel = rutinaViewModel,
                dia = dia
            )
        }
        composable(
            route = "detalleRutina/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val rutinaId = backStackEntry.arguments?.getInt("id") ?: 0
            Text("Detalle de rutina $rutinaId (en construcción)")
        }

        /*
        composable(
            route = "formRutinaEjercicios/{rutinaId}",
            arguments = listOf(navArgument("rutinaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val rutinaId = backStackEntry.arguments?.getInt("rutinaId") ?: 0
            RutinaEjercicioFormScreen(
                navController = navController,
                rutinaViewModel = rutinaViewModel,
                musculoViewModel = musculoViewModel,
                rutinaId = rutinaId
            )
        }
        composable("formMusculo") {
            MusculoFormScreen(navController, musculoViewModel)
        }
         */

    }
}
