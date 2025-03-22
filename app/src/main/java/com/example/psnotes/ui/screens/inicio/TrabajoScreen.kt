package com.example.psnotes.ui.screens.inicio

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.psnotes.ui.viewmodel.TrabajoViewModel

@Composable
fun TrabajoScreen() {

    val viewModel: TrabajoViewModel = viewModel()

    val tiempoTrabajado by viewModel.tiempoTrabajado.collectAsState()
    var trabajoRealizado by rememberSaveable { mutableStateOf(viewModel.trabajoRealizado.value) }
    val precioEstimado by viewModel.precioEstimado.collectAsState()
    var tarifaPorHora = viewModel.tarifaPorHora.collectAsState().value

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
                Text("Trabajo", style = typography.titleLarge, color = colorScheme.onBackground)
            }

            HorizontalDivider(thickness = 3.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Tarifa por hora (€):", style = typography.titleMedium, color = colorScheme.onBackground)
                Text(text = String.format("%.2f €", tarifaPorHora), style = typography.titleMedium, color = colorScheme.onBackground)
            }

            Slider(
                value = tarifaPorHora.toFloat(),
                onValueChange = { viewModel.setTarifa(it.toDouble()) },
                valueRange = 0f..100f,
                steps = 99,
                modifier = Modifier.fillMaxWidth()
            )

            HorizontalDivider(thickness = 3.dp)

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    text = "Trabajo realizado",
                    style = typography.titleMedium,
                    color = colorScheme.onBackground
                )

                TextField(
                    value = trabajoRealizado,
                    onValueChange = {
                        trabajoRealizado = it
                        viewModel.trabajoRealizado.value = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .heightIn(min = 100.dp)
                        .border(
                            width = 1.dp,
                            color = colorScheme.primary,
                            //shape = RoundedCornerShape(8.dp)
                        ),
                    placeholder = { Text("Escribe aquí...") }
                )

                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    text = "Tiempo de trabajo realizado",
                    style = typography.titleMedium
                )

                // Cuadro con el número
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            viewModel.decrementarTiempo()
                        },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(text = "-", style = typography.titleMedium)
                    }
                    Text(
                        text = "$tiempoTrabajado min",
                        style = typography.titleMedium,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )
                    IconButton(
                        onClick = {
                            viewModel.incrementarTiempo()
                        },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(text = "+", style = typography.titleMedium)
                    }
                }

                HorizontalDivider()

                Text(
                    modifier = Modifier.padding(vertical = 5.dp),
                    text = "Precio",
                    style = typography.titleMedium
                )

                // Cuadro con el número
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$precioEstimado €",
                        style = typography.titleMedium,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}