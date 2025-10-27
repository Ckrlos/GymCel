package cl.duocuc.gymcel.presentacion.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duocuc.gymcel.presentacion.ui.components.CardItem
import cl.duocuc.gymcel.presentacion.viewmodel.RutinaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutinasScreen(navController: NavController, viewModel: RutinaViewModel) {
    val rutinas = viewModel.rutinas.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Rutinas") },
                actions = {
                    IconButton(onClick = { navController.navigate("formRutina") }) {
                        Icon(Icons.Default.Add, contentDescription = "Añadir rutina")
                    }
                }
            )
        }
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (rutinas.value.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Aún no tienes rutinas registradas")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.navigate("formRutina") }) {
                        Text("Añadir rutina")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(rutinas.value) { rutina ->
                        CardItem {
                            Column {
                                Text(rutina.nombre, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    rutina.descripcion ?: "Sin descripción",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}