package com.example.psnotes.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.psnotes.ui.viewmodel.ClienteViewModel

@Composable
fun MiDesplegable(viewModel: ClienteViewModel) {
    val clientes = viewModel.state.clientes

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Selecciona un cliente") }

    Box() {
        Column() {
            Button(onClick = { expanded = !expanded }) {
                Text(selectedOption)
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Abrir menú")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                clientes.forEach { cliente ->
                    DropdownMenuItem(
                        text = { Text(cliente.commercialName) },
                        onClick = {
                            selectedOption = cliente.commercialName
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
