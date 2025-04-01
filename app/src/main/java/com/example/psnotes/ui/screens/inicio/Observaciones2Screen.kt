package com.example.psnotes.ui.screens.inicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.psnotes.ui.viewmodel.ObservacionesViewModel

@Composable
fun Observaciones2Screen(viewModel: ObservacionesViewModel = viewModel()) {
    val observacionesPrivadas by viewModel.observacionesPrivadas.collectAsState()

    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.Center)
        {
            Text("Observaciones privadas")
        }
        HorizontalDivider()
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = observacionesPrivadas,
                onValueChange = { viewModel.actualizarObservacionesPrivadas(it) },
                label = { Text("Observaciones privadas") },
            )
        }
    }
}