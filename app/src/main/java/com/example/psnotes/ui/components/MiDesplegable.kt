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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.psnotes.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiDesplegable(viewModel: HomeViewModel) {
    val clientes = viewModel.state.clientes

    var expanded by remember { mutableStateOf(false) }
    val selectedOption by remember { mutableStateOf("Selecciona un cliente") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = it
        },
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
                    text = { Text(cliente.commercialName) },
                    onClick = { expanded = false }
                )
            }
        }
    }
}
