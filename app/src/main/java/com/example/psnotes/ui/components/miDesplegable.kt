package com.example.psnotes.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.psnotes.ui.viewmodel.ClienteViewModel

@Composable
fun miDesplegable(modifier: Modifier, clienteViewModel: ClienteViewModel): String {
    val clientes = clienteViewModel.state.clientes

    var clienteId by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Selecciona un cliente") }

    Column(modifier = modifier) {
        Button(
            onClick = { expanded = !expanded },
            colors = ButtonColors(
                containerColor = colorScheme.primary,
                contentColor = colorScheme.onBackground,
                disabledContainerColor = Color.DarkGray,
                disabledContentColor = Color.Gray
            )
        ) {
            Text(
                text = selectedOption
            )
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Abrir menú")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            clientes.forEach { cliente ->
                DropdownMenuItem(text = {
                    Text(
                        cliente.commercialName
                    )
                }, onClick = {
                    selectedOption = cliente.commercialName
                    expanded = false
                    clienteId = cliente.id
                })
            }
        }
    }
    return clienteId

}
