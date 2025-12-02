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
import cl.duocuc.gymcel.AppRoutes
import cl.duocuc.gymcel.presentacion.factory.ApiServiceViewModelFactory
import cl.duocuc.gymcel.presentacion.factory.DatabaseViewModelFactory
import cl.duocuc.gymcel.presentacion.factory.GenericViewModelFactory
import cl.duocuc.gymcel.presentacion.ui.screens.ExerciseSearchScreen
import cl.duocuc.gymcel.presentacion.ui.ejercicio.ExerciseSearchViewModel
import cl.duocuc.gymcel.presentacion.ui.screens.ExerciseDetailScreen
import cl.duocuc.gymcel.presentacion.ui.screens.WorkoutLogScreen
import cl.duocuc.gymcel.presentacion.ui.screens.HomeScreen
import cl.duocuc.gymcel.presentacion.ui.screens.RutinaDetalleScreen
import cl.duocuc.gymcel.presentacion.ui.screens.SeleccionarRutinaScreen
import cl.duocuc.gymcel.presentacion.viewmodel.ExerciseDetailViewModel
import cl.duocuc.gymcel.presentacion.viewmodel.RutinaDetalleViewModel
import cl.duocuc.gymcel.presentacion.viewmodel.SeleccionarRutinaViewModel
import cl.duocuc.gymcel.presentacion.viewmodel.WorkoutLogViewModel
import cl.duocuc.gymcel.data.FactoryProvider
import cl.duocuc.gymcel.presentacion.viewmodel.HomeViewModel


@Composable
fun AppNavGraph(navController: NavHostController, context: Context) {
    val db = AppConstants.getDatabase(context)
    val apiService = AppConstants.getApiService()
    val registry = FactoryProvider.registry(db)


    NavHost(
        navController = navController,
        startDestination = AppRoutes.HOME()
    ) {

        composable(AppRoutes.HOME()) {
            HomeScreen(
                navController = navController,
                viewModel = viewModel(
                    factory = DatabaseViewModelFactory(
                        HomeViewModel::class.java,
                        db
                    ) { database -> HomeViewModel(database) }
                )
            )
        }

        composable("seleccionarRutina") {
            SeleccionarRutinaScreen(navController, viewModel(
                factory = DatabaseViewModelFactory(
                    SeleccionarRutinaViewModel::class.java,
                    db
                ) { database -> SeleccionarRutinaViewModel(database) }
            ))
        }

        composable("searchExercise") {
            ExerciseSearchScreen(
                viewModel = viewModel(
                    factory = ApiServiceViewModelFactory(ExerciseSearchViewModel::class.java,
                        apiService
                    ) { api -> ExerciseSearchViewModel(api) }
                ),
                onExerciseSelected = { id -> println("Ejercicio seleccionado: $id")},
                onOpenDetail = { id -> navController.navigate("exerciseDetail/$id") },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("exerciseDetail/{exerciseId}",
            arguments = listOf(navArgument("exerciseId") { type = NavType.StringType })
        ) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getString("exerciseId") ?: ""
            ExerciseDetailScreen(
                viewModel = viewModel(
                    factory = ApiServiceViewModelFactory(ExerciseDetailViewModel::class.java,
                        apiService
                    ) { api -> ExerciseDetailViewModel(api) }
                ),
                exerciseId = exerciseId,
                onSelect = { id -> println("Ejercicio seleccionado en detalle: $id") },
                onBackClick = { navController.popBackStack() }
            )
        }


        composable("rutinasPorDia") {
            WorkoutLogScreen(
                navController,
                viewModel = viewModel(
                    factory = GenericViewModelFactory(
                        WorkoutLogViewModel::class.java,
                        FactoryProvider.repositoryFactory(registry),
                    ) { param -> WorkoutLogViewModel(param) }
                    )
                )
        }


        composable(
            "treino_detalle/{treinoId}",
            arguments = listOf(
                navArgument("treinoId") { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val treinoId = navBackStackEntry.arguments?.getLong("treinoId") ?: 0L

            RutinaDetalleScreen(
                navController = navController,
                treinoId = treinoId,
                viewModel = viewModel(
                    factory = DatabaseViewModelFactory(
                        RutinaDetalleViewModel::class.java,
                        db
                    ) { db -> RutinaDetalleViewModel(db) }
                )
            )
        }


    }
}

