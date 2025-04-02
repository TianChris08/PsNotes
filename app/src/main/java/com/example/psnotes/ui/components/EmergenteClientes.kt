package com.example.psnotes.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.psnotes.ui.viewmodel.ClienteViewModel

@Composable
fun EmergenteClientes(onDismiss: () -> Unit, clienteViewModel: ClienteViewModel) : String? {
    val listaClientes = clienteViewModel.getClientes()
    val mostrarFormularioNuevoCliente = remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Seleccionar cliente") },
        text = {
            LazyColumn {
                for (cliente in listaClientes) {
                    item {
                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                            onClick = {
                                clienteViewModel.clienteSeleccionado.value = cliente
                                clienteViewModel.mostrarEmergenteClientes.value = false
                            }
                        ) {
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = cliente.commercialName,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    mostrarFormularioNuevoCliente.value = true
                }
            ) {
                Text("Añadir")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Salir") }
        }
    )
    if (mostrarFormularioNuevoCliente.value) {
        NuevoClienteForm(
            onDismiss = { mostrarFormularioNuevoCliente.value = false },
            clienteViewModel = clienteViewModel
        )
    }
    return clienteViewModel.clienteSeleccionado.value?.id
}