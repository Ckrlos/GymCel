package cl.duocuc.gymcel.presentacion

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cl.duocuc.gymcel.data.repository.MusculoRepositoryImpl
import cl.duocuc.gymcel.domain.usecase.GuardarMusculoUseCase
import cl.duocuc.gymcel.domain.usecase.ObtenerMusculosUseCase
import cl.duocuc.gymcel.presentacion.viewmodel.MusculoViewModel
import cl.duocuc.gymcel.presentation.ui.screens.HomeScreen
import cl.duocuc.gymcel.presentation.ui.screens.MusculoFormScreen

@Composable
fun AppNavGraph(navController: NavHostController, context: Context) {

    val repository = MusculoRepositoryImpl(context)
    val obtenerMusculosUseCase = ObtenerMusculosUseCase(repository)
    val guardarMusculoUseCase = GuardarMusculoUseCase(repository)


    val musculoViewModel = MusculoViewModel(
        obtenerMusculosUseCase = obtenerMusculosUseCase,
        guardarMusculoUseCase = guardarMusculoUseCase
    )

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController, musculoViewModel)
        }
        composable("formMusculo") {
            MusculoFormScreen(navController, musculoViewModel)
        }
    }
}
