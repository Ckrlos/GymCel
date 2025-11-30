// presentacion/ui/screens/RutinaDetalleScreen.kt
package cl.duocuc.gymcel.presentacion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.duocuc.gymcel.domain.model.Ejercicio
import cl.duocuc.gymcel.presentacion.ui.components.BottomNavBar
import cl.duocuc.gymcel.presentacion.ui.components.TopNavBar
import cl.duocuc.gymcel.presentacion.ui.viewmodels.RutinaDetalleViewModel
import cl.duocuc.gymcel.utils.CalculadoraPesos
import java.net.URL

// Modelos temporales solo para esta screen
data class SerieTemp(
    val numero: Int,
    val repsRealizadas: Int,
    val carga: Double,
    val unidad: String = "kg",
    val completada: Boolean = false
)

data class HistorialEjercicioTemp(
    val fecha: String,
    val ejercicioId: Int,
    val series: List<SerieTemp>,
    val mejorSerie: SerieTemp? = null
)

// Modelo temporal para datos de API
data class EjercicioApiTemp(
    val exerciseId: String,
    val name: String,
    val gifUrl: URL?,
    val targetMuscles: List<String>,
    val bodyParts: List<String>,
    val equipments: List<String>,
    val secondaryMuscles: List<String>,
    val instructions: List<String>
)

@Composable
fun RutinaDetalleScreen(
    navController: NavController,
    rutinaId: Int?,
    viewModel: RutinaDetalleViewModel = viewModel()
) {
    val rutina by viewModel.rutina.collectAsState()
    val ejercicios by viewModel.ejercicios.collectAsState()
    val historialVisible by viewModel.historialVisible.collectAsState()
    val cargando by viewModel.cargando.collectAsState()

    // Estado para unidad de medida global
    var unidadGlobal by remember { mutableStateOf("kg") }

    // Datos de entrenamiento temporal (solo para UI)
    val seriesPorEjercicio = remember {
        mapOf(
            1 to listOf(
                SerieTemp(numero = 1, repsRealizadas = 8, carga = 60.0),
                SerieTemp(numero = 2, repsRealizadas = 8, carga = 60.0),
                SerieTemp(numero = 3, repsRealizadas = 7, carga = 60.0)
            ),
            2 to listOf(
                SerieTemp(numero = 1, repsRealizadas = 10, carga = 0.0),
                SerieTemp(numero = 2, repsRealizadas = 10, carga = 0.0),
                SerieTemp(numero = 3, repsRealizadas = 9, carga = 0.0)
            ),
            3 to listOf(
                SerieTemp(numero = 1, repsRealizadas = 12, carga = 20.0),
                SerieTemp(numero = 2, repsRealizadas = 12, carga = 20.0),
                SerieTemp(numero = 3, repsRealizadas = 0, carga = 20.0)
            )
        )
    }

    val historialFicticio = remember {
        mapOf(
            1 to HistorialEjercicioTemp(
                fecha = "2024-01-15",
                ejercicioId = 1,
                series = listOf(
                    SerieTemp(numero = 1, repsRealizadas = 8, carga = 55.0),
                    SerieTemp(numero = 2, repsRealizadas = 8, carga = 55.0),
                    SerieTemp(numero = 3, repsRealizadas = 7, carga = 55.0)
                ),
                mejorSerie = SerieTemp(numero = 1, repsRealizadas = 8, carga = 55.0)
            )
        )
    }

    // Estado local para los datos de entrenamiento
    var seriesEstado by remember { mutableStateOf(seriesPorEjercicio) }

    // Cargar datos cuando cambia el rutinaId
    LaunchedEffect(rutinaId) {
        if (rutinaId != null) {
            viewModel.cargarRutina(rutinaId)
        }
    }

    Scaffold(
        topBar = {
            TopNavBar(
                title = rutina?.nombre ?: "Cargando...",
                showBackButton = true,
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = { BottomNavBar(navController = navController) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (cargando) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Cargando rutina...")
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Selector de unidad global
                    SelectorUnidad(
                        unidadSeleccionada = unidadGlobal,
                        onUnidadChange = { nuevaUnidad ->
                            unidadGlobal = nuevaUnidad
                            // Convertir todas las cargas a la nueva unidad
                            val nuevasSeries = seriesEstado.mapValues { (_, series) ->
                                series.map { serie ->
                                    val cargaConvertida = if (serie.unidad != nuevaUnidad) {
                                        CalculadoraPesos.convertirPeso(
                                            serie.carga,
                                            serie.unidad,
                                            nuevaUnidad
                                        )
                                    } else {
                                        serie.carga
                                    }
                                    serie.copy(carga = cargaConvertida, unidad = nuevaUnidad)
                                }
                            }
                            seriesEstado = nuevasSeries
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(ejercicios) { ejercicio ->
                            EjercicioItem(
                                ejercicio = ejercicio,
                                series = seriesEstado[ejercicio.id] ?: emptyList(),
                                historial = historialFicticio[ejercicio.id],
                                unidadGlobal = unidadGlobal,
                                onShowHistorial = { viewModel.mostrarHistorial(ejercicio.id) },
                                onUpdateSerie = { serieIndex, reps, carga ->
                                    // Actualizar datos ficticios localmente
                                    val nuevasSeries = seriesEstado.toMutableMap()
                                    val seriesEjercicio = nuevasSeries[ejercicio.id]?.toMutableList() ?: mutableListOf()

                                    if (serieIndex < seriesEjercicio.size) {
                                        seriesEjercicio[serieIndex] = seriesEjercicio[serieIndex].copy(
                                            repsRealizadas = reps,
                                            carga = carga
                                        )
                                        nuevasSeries[ejercicio.id] = seriesEjercicio
                                        seriesEstado = nuevasSeries
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }

            // Dialog para mostrar historial
            if (historialVisible != null) {
                val ejercicioHistorial = ejercicios.find { it.id == historialVisible }
                val historial = historialFicticio[historialVisible]

                if (ejercicioHistorial != null && historial != null) {
                    HistorialDialog(
                        ejercicio = ejercicioHistorial,
                        historial = historial,
                        onDismiss = { viewModel.ocultarHistorial() }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorUnidad(
    unidadSeleccionada: String,
    onUnidadChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = when (unidadSeleccionada) {
                "kg" -> "Kilogramos (kg)"
                "lb" -> "Libras (lb)"
                else -> unidadSeleccionada
            },
            onValueChange = {},
            readOnly = true,
            label = { Text("Unidad de peso") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Kilogramos (kg)") },
                onClick = {
                    onUnidadChange("kg")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Libras (lb)") },
                onClick = {
                    onUnidadChange("lb")
                    expanded = false
                }
            )
        }
    }
}

@Composable
fun EjercicioItem(
    ejercicio: Ejercicio,
    series: List<SerieTemp>,
    historial: HistorialEjercicioTemp?,
    unidadGlobal: String,
    onShowHistorial: () -> Unit,
    onUpdateSerie: (Int, Int, Double) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header del ejercicio con datos de API
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = ejercicio.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    // Información específica de la API
                    ejercicio.equipo?.let { equipo ->
                        if (equipo.isNotBlank()) {
                            Text(
                                text = "Equipo: $equipo",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    ejercicio.tipo?.let { tipo ->
                        if (tipo.isNotBlank()) {
                            Text(
                                text = "Tipo: $tipo",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }

                }

                // Botón de historial
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                        .clickable { onShowHistorial() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Ver historial anterior",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Encabezado de la tabla simplificado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Serie", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text("Carga ($unidadGlobal)", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.5f))
                Text("Reps", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text("Meta", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Series del ejercicio
            series.forEachIndexed { index, serie ->
                SerieRowEditable(
                    serie = serie,
                    expected = 10, // Valor fijo por ahora
                    serieNumber = index + 1,
                    unidadGlobal = unidadGlobal,
                    onRepsChange = { reps -> onUpdateSerie(index, reps, serie.carga) },
                    onCargaChange = { carga -> onUpdateSerie(index, serie.repsRealizadas, carga) }
                )
            }
        }
    }
}

@Composable
fun SerieRowEditable(
    serie: SerieTemp,
    expected: Int,
    serieNumber: Int,
    unidadGlobal: String,
    onRepsChange: (Int) -> Unit,
    onCargaChange: (Double) -> Unit
) {
    // Convertir la carga a la unidad global si es necesario
    val cargaMostrar = if (serie.unidad != unidadGlobal) {
        CalculadoraPesos.convertirPeso(serie.carga, serie.unidad, unidadGlobal)
    } else {
        serie.carga
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = serieNumber.toString(),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )

        // Campo editable para carga
        Box(
            modifier = Modifier
                .weight(1.5f)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                .clickable {
                    // Incremento inteligente basado en la unidad
                    val incremento = when (unidadGlobal) {
                        "kg" -> 2.5
                        "lb" -> 5.0
                        else -> 1.0
                    }
                    val nuevaCarga = CalculadoraPesos.convertirPeso(
                        cargaMostrar + incremento,
                        unidadGlobal,
                        serie.unidad
                    )
                    onCargaChange(nuevaCarga)
                }
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (cargaMostrar > 0) {
                    "${String.format("%.1f", cargaMostrar)} $unidadGlobal"
                } else {
                    "Peso corporal"
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Campo editable para reps
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                .clickable {
                    val nuevasReps = (serie.repsRealizadas + 1).coerceAtMost(50)
                    onRepsChange(nuevasReps)
                }
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = serie.repsRealizadas.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Text(
            text = expected.toString(),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun HistorialDialog(
    ejercicio: Ejercicio,
    historial: HistorialEjercicioTemp,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Entrenamiento Anterior",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Fecha: ${historial.fecha}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = ejercicio.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Encabezado de series del historial
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Serie", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    Text("Reps", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    Text("Carga", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Mostrar series del historial
                historial.series.forEach { serie ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Serie ${serie.numero}")
                        Text("${serie.repsRealizadas} reps")
                        Text(
                            if (serie.carga > 0) {
                                "${String.format("%.1f", serie.carga)} ${serie.unidad}"
                            } else {
                                "Peso corporal"
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                historial.mejorSerie?.let { mejorSerie ->
                    Text(
                        text = "🔥 Mejor serie: ${mejorSerie.repsRealizadas} reps con ${
                            if (mejorSerie.carga > 0) {
                                "${String.format("%.1f", mejorSerie.carga)} ${mejorSerie.unidad}"
                            } else {
                                "peso corporal"
                            }
                        }",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "💡 Compara tu progreso con el entrenamiento anterior",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}