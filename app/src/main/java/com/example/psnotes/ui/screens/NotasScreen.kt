package com.example.psnotes.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.psnotes.data.SessionManager
import com.example.psnotes.ui.viewmodel.ClienteViewModel
import com.example.psnotes.ui.viewmodel.NotaViewModel

@Composable
fun NotasScreen(
    context : Context,
    paddingValues: PaddingValues,
    notasViewModel: NotaViewModel,
    clienteViewModel: ClienteViewModel
) {
    val usuarioActual = SessionManager.getSavedLoginDetails(context)
    val nombreUsuario = usuarioActual.first

    Column (
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.weight(0.1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Text("Notas del usuario: $nombreUsuario",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        val notasSize = notasViewModel.notasState.size

        HorizontalDivider(modifier = Modifier)

        if (notasSize < 1) {
            Row(modifier = Modifier.weight(0.9f),
                verticalAlignment = Alignment.CenterVertically) {
                Text("No has creado notas aún.",
                    fontSize = 18.sp
                )
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().weight(0.7f)) {
                items(notasSize) { nota ->
                    val clienteId = clienteViewModel.clienteSeleccionado.value?.id ?: "Desconocido"
                    val nombreCliente = remember { mutableStateOf("Cargando...") }

                    LaunchedEffect(clienteId) {
                        clienteViewModel.buscarClientePorId(clienteId)
                    }

                    // Observar cambios en el nombre del cliente
                    nombreCliente.value = clienteViewModel.nombreCliente ?: "Desconocido"

                    //MATERIAL SELECCIONADO
                    Card(modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = "ID Nota: ${notasViewModel.notasState[nota].id}",
                                fontWeight = FontWeight.Thin
                            )
                            HorizontalDivider()

                            Text(text = "Cliente: ${nombreCliente.value}")
                            Text(text = "Trabajador: ${notasViewModel.notasState[nota].trabajadorId}")
                            Text(text = "Persona de contacto: ${notasViewModel.notasState[nota].personaContacto}")
                            Text(text = "Fecha: ${notasViewModel.notasState[nota].fecha ?: "Fecha no disponible"}")
                            Text(text = "Trabajo realizado: ${notasViewModel.notasState[nota].trabajoRealizado}")
                            Text(text = "Observaciones públicas: ${notasViewModel.notasState[nota].observacionesPublias ?: "Sin observaciones"}")
                            Text(text = "Observaciones privadas: ${notasViewModel.notasState[nota].observacionesPrivadas ?: "Sin observaciones"}")
                        }
                    }
                }
            }
        }
    }
}