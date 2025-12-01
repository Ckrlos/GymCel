package cl.duocuc.gymcel.presentacion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.duocuc.gymcel.presentacion.ui.viewmodels.RutinaDetalleViewModel
import cl.duocuc.gymcel.presentacion.ui.viewmodels.SerieUI
import cl.duocuc.gymcel.presentacion.ui.components.TopNavBar
import cl.duocuc.gymcel.presentacion.ui.components.BottomNavBar

@Composable
fun RutinaDetalleScreen(
    navController: NavController,
    rutinaId: Long,
    viewModel: RutinaDetalleViewModel = viewModel()
) {
    val rutina by viewModel.rutina.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val seriesUI by viewModel.seriesUI.collectAsState()
    val editable by viewModel.editable.collectAsState()

    LaunchedEffect(rutinaId) { viewModel.cargarRutina(rutinaId) }

    Scaffold(
        topBar = {
            TopNavBar(
                title = rutina?.nombre ?: "Rutina",
                showBackButton = true,
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->

        Box(modifier = Modifier.padding(padding)) {
            when {
                loading -> CenteredText("Cargando...")
                rutina == null -> CenteredText("Rutina no encontrada")
                else -> LazyColumn(modifier = Modifier.padding(12.dp)) {
                    items(seriesUI.entries.toList()) { (detalleId, series) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text("Ejercicio #$detalleId", style = MaterialTheme.typography.titleMedium)

                                Spacer(Modifier.height(12.dp))

                                SeriesTable(
                                    detalleId = detalleId,
                                    series = series,
                                    editable = editable,
                                    onCarga = viewModel::actualizarCarga,
                                    onReps = viewModel::actualizarReps,
                                    onUnidad = viewModel::actualizarUnidad
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun SeriesTable(
    detalleId: Long,
    series: List<SerieUI>,
    editable: Boolean,
    onCarga: (Long, Int, Double) -> Unit,
    onReps: (Long, Int, Int) -> Unit,
    onUnidad: (Long, Int, String) -> Unit
) {
    Column {
        series.forEachIndexed { index, serie ->

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${serie.numero}", Modifier.width(40.dp))

                // --- CARGA ---
                OutlinedTextField(
                    value = serie.carga.toString(),
                    onValueChange = {
                        it.toDoubleOrNull()?.let { v ->
                            onCarga(detalleId, index, v)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = serie.editable && editable,
                    label = { Text("Carga") }
                )

                // --- UNIDAD ---
                UnidadPicker(
                    selected = serie.unidad,
                    enabled = serie.editable && editable,
                    onSelect = { onUnidad(detalleId, index, it) }
                )

                // --- REPS ---
                OutlinedTextField(
                    value = serie.reps.toString(),
                    onValueChange = {
                        it.toIntOrNull()?.let { v -> onReps(detalleId, index, v) }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = serie.editable && editable,
                    label = { Text("Reps") }
                )

                // --- META (TOOLTIP) ---
                serie.meta?.let { meta ->
                    MetaTooltip(meta)
                }
            }
        }
    }
}


// Tooltip de meta
@Composable
private fun MetaTooltip(meta: String) {
    var show by remember { mutableStateOf(false) }

    Box(Modifier.padding(start = 8.dp)) {
        Text(
            "ⓘ",
            modifier = Modifier.clickable { show = true },
            color = MaterialTheme.colorScheme.primary
        )

        if (show) {
            Popup(onDismissRequest = { show = false }) {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(12.dp)
                ) {
                    Text("Meta: $meta")
                }
            }
        }
    }
}


// Picker de unidad
@Composable
private fun UnidadPicker(
    selected: String,
    enabled: Boolean,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(Modifier.padding(horizontal = 8.dp)) {

        Text(
            text = selected,
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable(enabled) { expanded = true }
                .padding(horizontal = 10.dp, vertical = 8.dp)
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            listOf("kg", "lb").forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onSelect(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun CenteredText(text: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text)
    }
}
