package cl.duocuc.gymcel.presentacion.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cl.duocuc.gymcel.domain.model.DetalleRutina
import cl.duocuc.gymcel.domain.model.TipoSerie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleRutinaCard(
    ejercicioNombre: String,
    ejercicioId: String,
    ordenInferido: Int,
    initial: DetalleRutina? = null,
    onValueChange: (DetalleRutina) -> Unit
) {
    var series by remember { mutableStateOf(initial?.series ?: 3) }
    var repsObjetivo by remember { mutableStateOf(initial?.objetivoReps ?: 10) }

    var isRange by remember { mutableStateOf(initial?.isRange() ?: false) }
    var rangoMin by remember { mutableStateOf(initial?.rangoReps?.first ?: 8) }
    var rangoMax by remember { mutableStateOf(initial?.rangoReps?.last ?: 12) }

    var tipoSerie by remember { mutableStateOf(initial?.tipoSerie ?: TipoSerie.STRAIGHT) }
    var tipoExpand by remember { mutableStateOf(false) }


    LaunchedEffect(series, repsObjetivo, isRange, rangoMin, rangoMax, tipoSerie) {
        val detalle = DetalleRutina(
            id = initial?.id ?: 0,
            ejercicioId = ejercicioId,
            orden = ordenInferido,
            series = series,
            objetivoReps = if (!isRange) repsObjetivo else null,
            rangoReps = if (isRange) rangoMin..rangoMax else null,
            tipoSerie = tipoSerie
        )
        onValueChange(detalle)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = ejercicioNombre,
                style = MaterialTheme.typography.titleMedium
            )

            Divider()

            OutlinedTextField(
                value = series.toString(),
                onValueChange = { series = it.toIntOrNull() ?: series },
                label = { Text("Series") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Usar rango de repeticiones")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = isRange,
                    onCheckedChange = { isRange = it }
                )
            }

            if (isRange) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = rangoMin.toString(),
                        onValueChange = { rangoMin = it.toIntOrNull() ?: rangoMin },
                        label = { Text("Mínimo") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = rangoMax.toString(),
                        onValueChange = { rangoMax = it.toIntOrNull() ?: rangoMax },
                        label = { Text("Máximo") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }
            } else {
                OutlinedTextField(
                    value = repsObjetivo.toString(),
                    onValueChange = { repsObjetivo = it.toIntOrNull() ?: repsObjetivo },
                    label = { Text("Repeticiones objetivo") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }


            ExposedDropdownMenuBox(
                expanded = tipoExpand,
                onExpandedChange = { tipoExpand = it }
            ) {
                OutlinedTextField(
                    value = tipoSerie.desc,
                    onValueChange = {},
                    label = { Text("Tipo de serie") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = tipoExpand,
                    onDismissRequest = { tipoExpand = false }
                ) {
                    TipoSerie.entries.forEach { tipo ->
                        DropdownMenuItem(
                            text = { Text(tipo.desc) },
                            onClick = {
                                tipoSerie = tipo
                                tipoExpand = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetalleRutinaCardPreview() {
    MaterialTheme {
        DetalleRutinaCard(
            ejercicioNombre = "Press Banca",
            ejercicioId = "bench_press_001",
            ordenInferido = 1,
            initial = null
        ) { detalle ->
            println("Cambio → $detalle")
        }
    }
}

