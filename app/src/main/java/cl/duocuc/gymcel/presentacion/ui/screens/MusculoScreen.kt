package cl.duocuc.gymcel.presentacion.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cl.duocuc.gymcel.presentacion.viewmodel.MusculoViewModel

@Composable
fun MusculoScreen(viewModel: MusculoViewModel) {
    val lista = viewModel.musculos.collectAsState()

    LazyColumn {
        items(lista.value) { musculo ->
            Text(text = "${musculo.nombre} (${musculo.grupo})")
        }
    }
}
