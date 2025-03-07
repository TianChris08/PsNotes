package com.example.psnotes.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.psnotes.ui.viewmodel.ClienteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiDesplegable(viewModel: ClienteViewModel) {
    val clientes = viewModel.clientes.value

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Selecciona un cliente") }
    val opciones = listOf("Opción 1", "Opción 2", "Opción 3")

    LaunchedEffect(Unit) {
        viewModel.obtenerClientes()
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = it
        } // Permite desplegar desde cualquier parte del TextField
    ) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true, // Evita edición manual
            label = { Text("Selecciona una opción") },
            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Abrir menú",
                        Modifier.clickable { expanded = !expanded }
                    )
                }
            },
            modifier = Modifier.menuAnchor() // Asegura que el menú se alinee correctamente
        )

        // Menú desplegable con los clientes
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            clientes.forEach { cliente ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption = cliente.nombreComerial  // Establecer el nombre comercial seleccionado
                        expanded = false  // Cerrar el menú
                    },
                    text = { Text(cliente.nombreComerial) }
                )
            }
        }
    }
}
