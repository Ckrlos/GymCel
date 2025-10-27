package cl.duocuc.gymcel.presentacion.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duocuc.gymcel.domain.model.Ejercicio
import cl.duocuc.gymcel.domain.model.Musculo
import cl.duocuc.gymcel.presentacion.viewmodel.MusculoViewModel
import cl.duocuc.gymcel.presentacion.viewmodel.RutinaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutinaEjercicioFormScreen(
    navController: NavController,
    rutinaViewModel: RutinaViewModel,
    musculoViewModel: MusculoViewModel,
    rutinaId: Int
) {
    val musculos by musculoViewModel.musculos.collectAsState()

    // Campos del formulario
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    val musculosSeleccionados = remember { mutableStateListOf<Musculo>() }
    var expanded by remember { mutableStateOf(false) }

    // Parámetros de sets
    var cantidadSeries by remember { mutableStateOf("3") }
    var repeticiones by remember { mutableStateOf("10") }
    var carga by remember { mutableStateOf("20") }
    var descanso by remember { mutableStateOf("60") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Añadir Ejercicio") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Detalles del ejercicio",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            // Nombre
            item {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del ejercicio") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Descripción
            item {
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción (opcional)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Selección múltiple de músculos
            item {
                Text("Músculos involucrados", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))

                if (musculos.isEmpty()) {
                    Text("No hay músculos registrados aún")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { navController.navigate("formMusculo") }) {
                        Text("➕ Crear nuevo músculo")
                    }
                } else {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = if (musculosSeleccionados.isEmpty())
                                ""
                            else musculosSeleccionados.joinToString { it.nombre },
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Seleccionar músculos") },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            musculos.forEach { musculo ->
                                val seleccionado = musculosSeleccionados.contains(musculo)
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(musculo.nombre)
                                            if (seleccionado) {
                                                Icon(
                                                    imageVector = Icons.Default.Add,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        }
                                    },
                                    onClick = {
                                        if (seleccionado)
                                            musculosSeleccionados.remove(musculo)
                                        else
                                            musculosSeleccionados.add(musculo)
                                    }
                                )
                            }
                            Divider()
                            DropdownMenuItem(
                                text = { Text("➕ Añadir nuevo músculo") },
                                onClick = {
                                    expanded = false
                                    navController.navigate("formMusculo")
                                }
                            )
                        }
                    }
                }
            }

            // Configuración de sets
            item {
                Text("Configuración de sets", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = cantidadSeries,
                    onValueChange = { cantidadSeries = it },
                    label = { Text("Número de series") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = repeticiones,
                    onValueChange = { repeticiones = it },
                    label = { Text("Repeticiones por serie") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = carga,
                    onValueChange = { carga = it },
                    label = { Text("Carga (kg)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = descanso,
                    onValueChange = { descanso = it },
                    label = { Text("Descanso (segundos)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Botones de acción
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            if (nombre.isNotBlank()) {
                                val ejercicio = Ejercicio(
                                    id = 0,
                                    nombre = nombre.trim(),
                                    descripcion = descripcion.ifBlank { null },
                                    imagen = null,
                                    tipo = null,
                                    equipo = null,
                                    dificultad = null
                                )

                                rutinaViewModel.guardarEjercicioCompleto(
                                    rutinaId = rutinaId,
                                    ejercicio = ejercicio,
                                    musculos = musculosSeleccionados,
                                    series = cantidadSeries.toIntOrNull() ?: 3,
                                    reps = repeticiones.toIntOrNull() ?: 10,
                                    carga = carga.toDoubleOrNull() ?: 0.0,
                                    descanso = descanso.toIntOrNull() ?: 60
                                )

                                // Limpia el formulario
                                nombre = ""
                                descripcion = ""
                                musculosSeleccionados.clear()
                                cantidadSeries = "3"
                                repeticiones = "10"
                                carga = "20"
                                descanso = "60"
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Guardar y añadir otro")
                    }

                    Spacer(Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (nombre.isNotBlank()) {
                                val ejercicio = Ejercicio(
                                    id = 0,
                                    nombre = nombre.trim(),
                                    descripcion = descripcion.ifBlank { null },
                                    imagen = null,
                                    tipo = null,
                                    equipo = null,
                                    dificultad = null
                                )

                                rutinaViewModel.guardarEjercicioCompleto(
                                    rutinaId = rutinaId,
                                    ejercicio = ejercicio,
                                    musculos = musculosSeleccionados,
                                    series = cantidadSeries.toIntOrNull() ?: 3,
                                    reps = repeticiones.toIntOrNull() ?: 10,
                                    carga = carga.toDoubleOrNull() ?: 0.0,
                                    descanso = descanso.toIntOrNull() ?: 60
                                )

                                // Navegar a SemanaScreen
                                navController.navigate("semana") {
                                    popUpTo("semana") { inclusive = true }
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Guardar y salir")
                    }
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}
