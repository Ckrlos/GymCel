package cl.duocuc.gymcel.presentacion.ui.ejercicio

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duocuc.gymcel.domain.model.Ejercicio
import cl.duocuc.gymcel.presentacion.ui.components.EjercicioListItem

@Composable
fun ExerciseSearchScreen(
    viewModel: ExerciseSearchViewModel,
    onExerciseSelected: (String) -> Unit,
    onOpenDetail: (String) -> Unit
) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        TextField(
            value = state.query,
            onValueChange = { viewModel.onEvent(ExerciseSearchEvent.OnQueryChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Buscar ejercicio...") }
        )

        if (state.isLoading) {
            Text(
                text = "Buscando...",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        state.error?.let { errorMsg ->
            Text(
                text = errorMsg,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.results) { ejercicio: Ejercicio ->
                EjercicioListItem(
                    ejercicio = ejercicio,
                    onItemClick = {
                        // Selecciona y devuelve al llamador
                        onExerciseSelected(ejercicio.id)
                    },
                    onIconClick = {
                        // Abre detalle
                        onOpenDetail(ejercicio.id)
                    }
                )
            }
        }
    }
}
