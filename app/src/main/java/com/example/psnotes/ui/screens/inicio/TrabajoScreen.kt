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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.psnotes.viewmodel.TrabajoViewModel

@Composable
fun TrabajoScreen() {

    var viewModel: TrabajoViewModel = viewModel()

    var counter by rememberSaveable { mutableIntStateOf(viewModel.counter.value) }
    var trabajoRealizado by rememberSaveable { mutableStateOf(viewModel.trabajoRealizado.value) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .border(3.dp, color = colorScheme.inversePrimary)
                .padding(bottom = 5.dp),
            horizontalArrangement = Arrangement.Center)
        {
            Text("Trabajo", style = typography.titleLarge)
        }

        HorizontalDivider(thickness = 3.dp)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .border(3.dp, color = colorScheme.inversePrimary),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Text(
                modifier = Modifier.padding(vertical = 5.dp),
                text = "Trabajo realizado",
                style = typography.titleMedium
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
                        shape = RoundedCornerShape(8.dp)),
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
                        if (counter > 0) counter -= 30 // Evitar que sea negativo
                        viewModel.counter.value = counter // Guardar el estado en el ViewModel
                    },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(text = "-", style = typography.titleMedium)
                }
                Text(
                    text = "$counter min",
                    style = typography.titleMedium,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
                IconButton(
                    onClick = {
                        counter += 30
                        viewModel.counter.value = counter // Guardar el estado en el ViewModel
                    },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(text = "+", style = typography.titleMedium)
                }
            }

            HorizontalDivider()
        }
    }
}