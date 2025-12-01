package cl.duocuc.gymcel.presentacion.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cl.duocuc.gymcel.domain.model.DetalleEjercicio
import cl.duocuc.gymcel.domain.model.Ejercicio
import coil.compose.AsyncImage
import java.net.URL

@Composable
fun DetalleEjercicioScreen(
    ejercicio: Ejercicio,
    modifier: Modifier = Modifier
) {
    val detalle = ejercicio.detalle

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // GIF centrado y grande
        ejercicio.gif?.let { url ->
            AsyncImage(
                model = url.toString(),
                contentDescription = ejercicio.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
            )

            Spacer(Modifier.height(16.dp))
        }

        Text(
            text = ejercicio.nombre,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(20.dp))

        detalle?.let {

            // Músculos principales
            Text(
                text = "Músculos trabajados",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                it.musculosTrabajados.forEach { musculo ->
                    Box(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                RoundedCornerShape(50)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(musculo, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Equipamiento
            Text(
                "Equipamiento",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                it.equipamiento.forEach { eq ->
                    Box(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.secondaryContainer,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(eq, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Instrucciones numeradas
            Text(
                "Instrucciones",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                it.instrucciones.forEachIndexed { index, paso ->
                    Row {
                        Text(
                            "${index + 1}. ",
                            fontWeight = FontWeight.Bold
                        )
                        Text(paso)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetalleEjercicioScreen() {
    DetalleEjercicioScreen(
        ejercicio = Ejercicio(
            id = "1",
            nombre = "Sentadilla con barra",
            gif = URL("https://static.exercisedb.dev/media/oR7O9LW.gif"),
            detalle = DetalleEjercicio(
                musculosTrabajados = listOf("Cuádriceps", "Glúteos", "Isquiotibiales"),
                equipamiento = listOf("Barra", "Discos"),
                instrucciones = listOf(
                    "Coloca la barra sobre tus trapecios.",
                    "Desciende manteniendo la espalda recta.",
                    "Empuja con fuerza hacia arriba."
                )
            )
        )
    )
}