package cl.duocuc.gymcel.presentacion.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.duocuc.gymcel.presentacion.ui.components.BottomNavBar
import cl.duocuc.gymcel.presentacion.ui.components.RutinaCard
import cl.duocuc.gymcel.presentacion.ui.components.TopNavBar
import cl.duocuc.gymcel.presentacion.viewmodel.RutinasPorDiaViewModel
import java.time.LocalDate
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialTreino(
    navController: NavController,
    viewModel: RutinasPorDiaViewModel = viewModel()
) {
    val rutinas = viewModel.rutinas.collectAsState().value
    val treinos = viewModel.treinosPorRutina.collectAsState().value

    Scaffold(
        topBar = { TopNavBar() },
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("seleccionarRutina")
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Crear Rutina",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                "Rutinas",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            rutinas.forEach { rutina ->
                val totalTreinos = treinos[rutina.id] ?: 0

                RutinaCard(
                    rutina = rutina,
                    treinosRealizados = totalTreinos,
                    onClick = {
                        navController.navigate("rutinaDetalle/${rutina.id}")
                    }
                )
            }
        }
    }
}
