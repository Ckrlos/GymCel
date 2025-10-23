package cl.duocuc.gymcel.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duocuc.gymcel.presentacion.ui.components.CardItem
import cl.duocuc.gymcel.presentacion.ui.components.PrimaryButton
import cl.duocuc.gymcel.presentacion.viewmodel.MusculoViewModel


@Composable
fun HomeScreen(navController: NavController, viewModel: MusculoViewModel) {
    val lista = viewModel.musculos.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        if (lista.value.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Aún no tienes músculos registrados", fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(20.dp))
                PrimaryButton(text = "Añadir", onClick = {
                    navController.navigate("formMusculo")
                })
            }
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text("Tus músculos", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(lista.value) { musculo ->
                        CardItem(
                            onClick = { /* podrías navegar a detalles si quieres */ }
                        ) {
                            Column {
                                Text(
                                    musculo.nombre,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    "Grupo: ${musculo.grupo}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                PrimaryButton(text = "Añadir otro", onClick = {
                    navController.navigate("formMusculo")
                })
            }
        }
    }
}
