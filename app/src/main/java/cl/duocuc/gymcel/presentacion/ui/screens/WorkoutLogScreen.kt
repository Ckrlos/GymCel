package cl.duocuc.gymcel.presentacion.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.duocuc.gymcel.AppRoutes
import cl.duocuc.gymcel.presentacion.ui.components.BottomNavBar
import cl.duocuc.gymcel.presentacion.ui.components.TopNavBar
import cl.duocuc.gymcel.presentacion.ui.components.TreinoCard
import cl.duocuc.gymcel.presentacion.viewmodel.WorkoutLogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutLogScreen(
    navController: NavController,
    viewModel: WorkoutLogViewModel = viewModel()
) {
    // 1. Recolectar la lista de Treinos (usando el nuevo StateFlow)
    val treinosLog by viewModel.treinosUI.collectAsState()

    Scaffold(
        topBar = { TopNavBar() },
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                // Navega a la pantalla para INICIAR un nuevo treino (seleccionando una Rutina)
                onClick = {
                    navController.navigate(AppRoutes.SELECTOR_RUTINA())
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Iniciar nuevo treino",
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
                .padding(horizontal = 16.dp)
        ) {

            Text(
                "Historial de Treinos",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Usar LazyColumn para eficiencia al listar
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                // 3. Usar el DTO TreinoLogUI y el nuevo TreinoCard
                items(treinosLog, key = { it.treinoId }) { treino ->
                    TreinoCard(
                        treino = treino,
                        onClick = {
                            navController.navigate(AppRoutes.DETALLE_TREINO(treino.treinoId))
                        }
                    )
                }
            }

            if (treinosLog.isEmpty()) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    "Aún no has completado ningún treino.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}