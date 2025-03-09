package com.example.psnotes.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cable
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.psnotes.ui.components.MiDesplegable
import com.example.psnotes.ui.screens.inicio.FirmaScreen
import com.example.psnotes.ui.screens.inicio.MaterialesScreen
import com.example.psnotes.ui.screens.inicio.Observaciones1Screen
import com.example.psnotes.ui.screens.inicio.Observaciones2Screen
import com.example.psnotes.ui.screens.inicio.TrabajoScreen
import com.example.psnotes.ui.viewmodel.ClienteViewModel


@Composable
fun InicioScreen(paddingValues: PaddingValues) {
    val clienteViewModel: ClienteViewModel = viewModel()
    // Estado para controlar qué vista mostrar en la parte inferior
    val selectedSection = remember { mutableStateOf("trabajo") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // 🔹 Parte Superior - Información fija
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                .border(width = 1.dp, color = colorScheme.onBackground)
                .padding(horizontal = 10.dp)
        ) {
            MiDesplegable(viewModel = clienteViewModel)
            Text("Información Principal", style = MaterialTheme.typography.titleMedium)
            Text("Detalles sobre la pantalla...", style = MaterialTheme.typography.bodyMedium)
        }


        // 🔹 Parte Inferior - Contenido Dinámico
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.7f),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f) // Toma la mitad del espacio
            ) {
                when (selectedSection.value) {
                    "trabajo" -> TrabajoScreen()
                    "materiales" -> MaterialesScreen()
                    "firma" -> FirmaScreen()
                    "observaciones1" -> Observaciones1Screen()
                    "observaciones2" -> Observaciones2Screen()
                }
            }

            // 🔹 Botones de Cambio
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                val shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)

                listOf(
                    Icons.Filled.Work to "trabajo",
                    Icons.Filled.Cable to "materiales",
                    Icons.Filled.TextFormat to "firma",
                    Icons.Filled.NoteAlt to "observaciones1",
                    Icons.Outlined.NoteAlt to "observaciones2"
                ).forEach { (icon, text) ->
                    FloatingActionButton(
                        onClick = { selectedSection.value = text },
                        shape = shape,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                    ) {
                        Icon(icon, contentDescription = text)
                    }
                }
            }
        }
    }
}