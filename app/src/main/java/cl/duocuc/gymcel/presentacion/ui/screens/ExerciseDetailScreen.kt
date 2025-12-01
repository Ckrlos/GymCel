package cl.duocuc.gymcel.presentacion.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duocuc.gymcel.presentacion.ui.components.DetalleEjercicioCard
import cl.duocuc.gymcel.presentacion.viewmodel.ExerciseDetailUiState
import cl.duocuc.gymcel.presentacion.viewmodel.ExerciseDetailViewModel

@Composable
fun ExerciseDetailScreen(
    viewModel: ExerciseDetailViewModel,
    exerciseId: String,
    onSelect: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    // cargar solo una vez
    LaunchedEffect(exerciseId) {
        viewModel.loadExercise(exerciseId)
    }

    when (state) {
        ExerciseDetailUiState.Loading -> {
            Text("Cargando...")
        }

        is ExerciseDetailUiState.Error -> {
            Text("Error: ${(state as ExerciseDetailUiState.Error).mensaje}")
        }

        is ExerciseDetailUiState.Success -> {
            val ejercicio = (state as ExerciseDetailUiState.Success).ejercicio

            Column {
                Button(
                    onClick = { onSelect(ejercicio.id) },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Usar este ejercicio")
                }

                DetalleEjercicioCard(
                    ejercicio = ejercicio,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
