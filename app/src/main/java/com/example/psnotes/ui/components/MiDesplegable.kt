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
import androidx.compose.ui.graphics.Color
import com.example.psnotes.ui.viewmodel.ClienteViewModel

@Composable
fun MiDesplegable(viewModel: ClienteViewModel) {
    val clientes = viewModel.state.clientes

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Selecciona un cliente") }


    Column {
        Button(
            onClick = { expanded = !expanded }, colors = ButtonColors(
                containerColor = colorScheme.primary,
                contentColor = Color.Black,
                disabledContainerColor = Color.DarkGray,
                disabledContentColor = Color.Gray
            )
        ) {
            Text(
                color = Color.Black,
                text = selectedOption,
            )
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Abrir menú")
        }

        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }) {
            clientes.forEach { cliente ->
                DropdownMenuItem(text = {
                    Text(
                        cliente.commercialName
                    )
                }, onClick = {
                    selectedOption = cliente.commercialName
                    expanded = false
                })
            }
        }
    }

}
