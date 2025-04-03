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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.psnotes.ui.viewmodel.TrabajoViewModel
import java.util.Locale

@Composable
fun TrabajoScreen(trabajoViewModel: TrabajoViewModel) {

    val tiempoTrabajado by trabajoViewModel.tiempoTrabajado.collectAsState()
    var trabajoRealizado by rememberSaveable { mutableStateOf(trabajoViewModel.trabajoRealizado.value) }
    val precioEstimado by trabajoViewModel.precioManoDeObra.collectAsState()
    val tarifaPorHora = trabajoViewModel.tarifaPorHora.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center
            )
            {
                Text(
                    text = "Trabajo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            HorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Tarifa por hora (€):")
                Text(text = String.format(Locale.getDefault(), "%.2f €", tarifaPorHora))
            }

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
                        trabajoViewModel.trabajoRealizado.value = it
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
                            trabajoViewModel.decrementarTiempo()
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
                            trabajoViewModel.incrementarTiempo()
                        },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(text = "+", style = typography.titleMedium)
                    }
                }
            }
        }
    }
}