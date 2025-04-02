package com.example.psnotes.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.psnotes.ui.viewmodel.ClienteViewModel
import com.example.psnotes.ui.viewmodel.NotaViewModel

@Composable
fun NotasScreen(
    paddingValues: PaddingValues,
    notasViewModel: NotaViewModel,
    clienteViewModel: ClienteViewModel
) {

    //val nombreCliente = clienteViewModel.buscarClientePorId()
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {

        val notasSize = notasViewModel.notasState.size

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(notasSize) { nota ->
                val clienteId = clienteViewModel.clienteSeleccionado.value?.id ?: "nulo"
                val nombreCliente = remember { mutableStateOf("Cargando...") }

                LaunchedEffect(clienteId) {
                    clienteViewModel.buscarClientePorId(clienteId)
                }

                // Observar cambios en el nombre del cliente
                nombreCliente.value = clienteViewModel.nombreCliente ?: "Desconocido"

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