package com.example.psnotes.ui.screens.inicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.psnotes.ui.viewmodel.ObservacionesViewModel

@Composable
fun Observaciones1Screen(viewModel: ObservacionesViewModel = viewModel()) {

    val observacionesPublicas by viewModel.observacionesPublicas.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().weight(0.15f).padding(10.dp),
            horizontalArrangement = Arrangement.Center)
        {
            Text("Observaciones públicas",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp)
        }
        HorizontalDivider()
        Column(
            modifier = Modifier.weight(0.85f).padding(vertical = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                modifier = Modifier.fillMaxSize(),
                value = observacionesPublicas,
                onValueChange = { viewModel.actualizarObservacionesPublicas(it) },
                label = { Text("Observaciones") },
            )
        }
    }
}