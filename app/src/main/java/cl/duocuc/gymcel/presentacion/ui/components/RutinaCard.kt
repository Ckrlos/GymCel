package cl.duocuc.gymcel.presentacion.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duocuc.gymcel.domain.model.Rutina
import java.time.LocalDate

@Composable
fun RutinaCard(
    rutina: Rutina,
    treinosRealizados: Int,
    onClick: () -> Unit
) {
    val hoy = LocalDate.now().dayOfWeek
    val esHoy = rutina.dia == hoy

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = rutina.nombre,
                    style = MaterialTheme.typography.titleMedium
                )

                if (esHoy) {
                    Spacer(Modifier.width(8.dp))
                    AssistChip(onClick = {}, label = { Text("Hoy") })
                }
            }

            rutina.descripcion?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(top = 6.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            rutina.dia?.let {
                Text(
                    text = "Día: ${it.name}",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Text(
                text = "Treinos realizados: $treinosRealizados",
                modifier = Modifier.padding(top = 12.dp),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
