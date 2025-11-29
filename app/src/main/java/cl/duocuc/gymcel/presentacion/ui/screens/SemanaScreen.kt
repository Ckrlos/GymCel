package cl.duocuc.gymcel.presentacion.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duocuc.gymcel.presentacion.viewmodel.SemanaViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SemanaScreen(
    navController: NavController,
    viewModel: SemanaViewModel
) {
    val rutinas = viewModel.rutinas.collectAsState()

    val diasSemana = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")

    val diaActual = remember {
        LocalDate.now().dayOfWeek
            .getDisplayName(TextStyle.FULL, Locale("es", "ES"))
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    var diaSeleccionado by remember { mutableStateOf(diaActual) }
    LaunchedEffect(Unit) {
        viewModel.cargarRutinas()
    }

    val rutinaSeleccionada = rutinas.value
        .find { it.rutina.dia.equals(diaSeleccionado, ignoreCase = true) }

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        val index = diasSemana.indexOf(diaSeleccionado).coerceAtLeast(0)
        val offset = index * 180 // ancho aproximado de botón
        scrollState.scrollTo(offset)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Plan Semanal de Entrenamiento xdxdxdxdddddd",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🔹 Subtítulo
            Text(
                text = "Selecciona un día para ver o crear tu rutina personalizada",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Scroll horizontal de días
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                diasSemana.forEach { dia ->
                    val isSelected = dia == diaSeleccionado
                    Button(
                        onClick = { diaSeleccionado = dia },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = if (isSelected)
                                Color.White
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                    ) {
                        Text(
                            text = dia,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = "Desliza para ver más días",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (rutinaSeleccionada == null) {
                // 🟠 Si no hay rutina creada
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(60.dp))
                    Text(
                        text = "No hay rutina para $diaSeleccionado aún",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            navController.navigate("formRutina?dia=$diaSeleccionado")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(30.dp),
                        modifier = Modifier
                            .shadow(3.dp, RoundedCornerShape(30.dp))
                            .padding(top = 4.dp)
                    ) {
                        Text("Crear rutina para $diaSeleccionado")
                    }
                }
            } else {
                // 🟢 Si hay rutina creada
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        // 🔹 Título con ícono de edición/agregado
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = rutinaSeleccionada.rutina.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(
                                onClick = {
                                    navController.navigate("formRutinaEjercicios/${rutinaSeleccionada.rutina.id}")
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Agregar ejercicio",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = rutinaSeleccionada.rutina.desc ?: "Sin descripción",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Divider(color = Color.Gray.copy(alpha = 0.4f))
                    }

                    // 🔹 Ejercicios de la rutina
                    items(rutinaSeleccionada.entries) { entry ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = entry.ejercicio.ejercicio.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )

                                val musculos = entry.ejercicio.musculos.joinToString(", ") { it.name }
                                if (musculos.isNotBlank()) {
                                    Text(
                                        text = "Músculos: $musculos",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                if (entry.sets.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    entry.sets.forEachIndexed { index, set ->
                                        Text(
                                            text = "• Set ${index + 1}: ${set.reps ?: 0} reps  |  Carga: ${set.load ?: 0.0} ${set.load_unit}  |  Descanso: ${set.rest_time_sec ?: 0}s",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                } else {
                                    Text(
                                        text = "Sin sets definidos",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    item { Spacer(modifier = Modifier.height(24.dp)) }
                }
            }
        }
    }
}
