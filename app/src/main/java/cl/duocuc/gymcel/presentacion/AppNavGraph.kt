package cl.duocuc.gymcel.presentacion

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cl.duocuc.gymcel.AppConstants
import cl.duocuc.gymcel.AppRoutes
import cl.duocuc.gymcel.core.navigation.composable
import cl.duocuc.gymcel.presentacion.factory.ApiServiceViewModelFactory
import cl.duocuc.gymcel.presentacion.factory.DatabaseViewModelFactory
import cl.duocuc.gymcel.presentacion.factory.GenericViewModelFactory
import cl.duocuc.gymcel.presentacion.ui.screens.ExerciseSearchScreen
import cl.duocuc.gymcel.presentacion.ui.ejercicio.ExerciseSearchViewModel
import cl.duocuc.gymcel.presentacion.ui.screens.ExerciseDetailScreen
import cl.duocuc.gymcel.presentacion.ui.screens.WorkoutLogScreen
import cl.duocuc.gymcel.presentacion.ui.screens.HomeScreen
import cl.duocuc.gymcel.presentacion.ui.screens.DetalleTreinoScreen
import cl.duocuc.gymcel.presentacion.ui.screens.SeleccionarRutinaScreen
import cl.duocuc.gymcel.presentacion.viewmodel.ExerciseDetailViewModel
import cl.duocuc.gymcel.presentacion.viewmodel.DetalleTreinoViewModel
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

        composable(AppRoutes.SELECTOR_RUTINA()) {
            SeleccionarRutinaScreen(navController, viewModel(
                factory = DatabaseViewModelFactory(
                    SeleccionarRutinaViewModel::class.java,
                    db
                ) { database -> SeleccionarRutinaViewModel(database) }
            )) { id -> navController.navigate(AppRoutes.DETALLE_TREINO(id)) }
        }

        composable(AppRoutes.BUSCAR_EJERCICIO()) {
            ExerciseSearchScreen(
                viewModel = viewModel(
                    factory = ApiServiceViewModelFactory(ExerciseSearchViewModel::class.java,
                        apiService
                    ) { api -> ExerciseSearchViewModel(api) }
                ),
                onExerciseSelected = { exercise -> println("Ejercicio seleccionado: $exercise")},
                onOpenDetail = { id -> navController.navigate(AppRoutes.DETALLE_EJERCICIO(id)) },
                onBackClick = { navController.popBackStack() }
            )
        }

        AppRoutes.DETALLE_EJERCICIO.composable(this) { params ->
            // lo dejamos nuleable porque la pantalla maneja el caso null internamente.
            val exerciseId = params.getOrNull(0)

            ExerciseDetailScreen(
                viewModel = viewModel(
                    factory = ApiServiceViewModelFactory(
                        ExerciseDetailViewModel::class.java,
                        apiService
                    ) { api -> ExerciseDetailViewModel(api) }
                ),
                exerciseId = exerciseId,
                onSelect = { id -> println("Ejercicio seleccionado en detalle: $id") },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.WORKOUT_LOG()) {
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

        AppRoutes.DETALLE_TREINO.composable(this, navType = NavType.LongType) { params ->
            //FIXME: que pasa en la condicion que caiga en 0L ? como estamos manejando ese caso...
            val treinoId = params[0]?.toLongOrNull() ?: 0L

            DetalleTreinoScreen(
                navController = navController,
                treinoId = treinoId,
                viewModel = viewModel(
                    factory = DatabaseViewModelFactory(
                        DetalleTreinoViewModel::class.java,
                        db
                    ) { db -> DetalleTreinoViewModel(db) }
                )
            )
        }


    }
}

