package com.example.psnotes.ui.screens.inicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.psnotes.ui.viewmodel.TrabajoViewModel

@Composable
fun MaterialesScreen() {

    val viewModel: TrabajoViewModel = viewModel()

    val cantidadMateriales by rememberSaveable { mutableStateOf(0) }

    /*val tiempoTrabajado by viewModel.tiempoTrabajado.collectAsState()
    var trabajoRealizado by rememberSaveable { mutableStateOf(viewModel.trabajoRealizado.value) }
    val precioEstimado by viewModel.precioEstimado.collectAsState()
    val tarifaPorHora by viewModel.tarifaPorHora.collectAsState()*/

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.Center
            )
            {
                Text("Materiales", style = typography.titleLarge, color = colorScheme.onBackground)
            }

            HorizontalDivider(thickness = 3.dp)

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    text = "Materiales utilizados",
                    style = typography.titleMedium
                )

                Row {
                    Text("Añadir material")
                    IconButton(onClick = { /*TODO*/ }) {
                        Icons.Default.Add
                    }
                }
            }
        }
    }
}