package cl.duocuc.gymcel.presentacion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.duocuc.gymcel.presentacion.ui.components.BottomNavBar
import cl.duocuc.gymcel.presentacion.ui.components.TopNavBar
import cl.duocuc.gymcel.presentacion.ui.viewmodels.RutinaDetalleViewModel
import cl.duocuc.gymcel.presentacion.ui.viewmodels.SerieUI
import cl.duocuc.gymcel.domain.model.UnidadPeso
import cl.duocuc.gymcel.presentacion.ui.components.CenteredText
import cl.duocuc.gymcel.presentacion.ui.components.ExerciseCard
import cl.duocuc.gymcel.presentacion.ui.components.UltimoTreinoDialog

@Composable
fun RutinaDetalleScreen(
    navController: NavController,
    rutinaId: Long,
    viewModel: RutinaDetalleViewModel = viewModel()
) {
    val rutina by viewModel.rutina.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val seriesUI by viewModel.seriesUI.collectAsState()
    val editable by viewModel.editable.collectAsState()
    val popupData by viewModel.popupUltimoTreino.collectAsState()
    val popupEjercicio by viewModel.popupEjercicio.collectAsState()
    val detallesRutina = viewModel.detallesRutina.collectAsState()

    LaunchedEffect(rutinaId) { viewModel.cargarRutina(rutinaId) }

    Scaffold(
        topBar = {
            TopNavBar(
                title = rutina?.nombre ?: "Rutina",
                showBackButton = true,
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = {
            if (editable) {
                ExtendedFloatingActionButton(
                    onClick = { viewModel.guardarTreino() },
                    icon = { Icon(Icons.Filled.CheckCircle, contentDescription = null) },
                    text = { Text("Terminar") },
                    containerColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { padding ->

        // --- POPUP ULTIMO TREINO ---
        if (popupData != null && popupEjercicio != null) {
            UltimoTreinoDialog(
                ejercicio = popupEjercicio!!,
                series = popupData!!,
                onDismiss = { viewModel.cerrarPopupUltimoTreino() }
            )
        }

        Box(modifier = Modifier.padding(padding)) {
            when {
                loading -> CenteredText("Cargando...")
                rutina == null -> CenteredText("Rutina no encontrada")
                else -> LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(seriesUI.entries.toList()) { (detalleId, series) ->
                        val detalle = detallesRutina.value.find { it.id == detalleId } ?: return@items
                        ExerciseCard(
                            exerciseName = detalle.exercise_externalid,
                            series = series,
                            detalleId = detalleId,
                            editable = editable,
                            onShowLastWorkout = { viewModel.mostrarUltimoTreino(detalle) },
                            onCarga = viewModel::actualizarCarga,
                            onReps = viewModel::actualizarReps,
                            onUnidad = viewModel::actualizarUnidad
                        )
                    }
                }
            }
        }
    }
}
