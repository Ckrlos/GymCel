package cl.duocuc.gymcel.presentacion.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cl.duocuc.gymcel.domain.model.Rutina
import cl.duocuc.gymcel.presentacion.factory.RutinaViewModelFactory
import cl.duocuc.gymcel.presentacion.ui.components.PrimaryButton
import cl.duocuc.gymcel.presentacion.viewmodel.RutinaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutinaFormScreen(
    navController: NavController,
    viewModel: RutinaViewModel,
    dia: String? = null
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Rutina", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    "Crear nueva rutina",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        showError = false
                    },
                    label = { Text("Nombre de la rutina") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción (opcional)") },
                    modifier = Modifier.fillMaxWidth()
                )

                if (showError) {
                    Text(
                        text = "El nombre es obligatorio",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                PrimaryButton(
                    text = "Guardar rutina",
                    onClick = {
                        if (nombre.isBlank()) {
                            showError = true
                        } else {
                            val nuevaRutina = Rutina(
                                id = 0,
                                nombre = nombre.trim(),
                                descripcion = descripcion.ifBlank { null },
                                dia = dia
                            )

                            viewModel.guardarRutinaYObtenerId(nuevaRutina) { nuevaRutinaId ->
                                navController.navigate("formRutinaEjercicios/$nuevaRutinaId")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
/*
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun RutinaFormScreenPreview() {
    // Usamos un tema Material3 para el preview
    MaterialTheme {
        RutinaFormScreen(
            navController = rememberNavController(), // Necesitarás importar esto
            viewModel =
            dia = "Lunes"
        )
    }
}
 */