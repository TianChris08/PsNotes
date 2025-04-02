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

        val notasSize = notasViewModel.state.notas.size

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(notasSize) { nota ->
                val clienteId = notasViewModel.state.notas[nota].clienteId.toString()
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
                            text = "ID Nota: ${notasViewModel.state.notas[nota].id}",
                            fontWeight = FontWeight.Thin
                            )
                        HorizontalDivider()

                        Text(text = "Empresa: ${nombreCliente.value}")
                        Text(text = "Trabajador: ${notasViewModel.state.notas[nota].trabajadorId}")
                        Text(text = "Persona de contacto: ${notasViewModel.state.notas[nota].personaContacto}")
                        Text(text = "Fecha: ${notasViewModel.state.notas[nota].fecha ?: "Fecha no disponible"}")
                        Text(text = "Trabajo realizado: ${notasViewModel.state.notas[nota].trabajoRealizado}")
                        Text(text = "Observaciones públicas: ${notasViewModel.state.notas[nota].observacionesPublias ?: "Sin observaciones"}")
                        Text(text = "Observaciones privadas: ${notasViewModel.state.notas[nota].observacionesPrivadas ?: "Sin observaciones"}")
                    }
                }
            }
        }
    }

}