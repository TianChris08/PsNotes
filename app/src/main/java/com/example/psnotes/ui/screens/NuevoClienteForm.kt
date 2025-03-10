package com.example.psnotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NuevoClienteForm(onDismiss: () -> Unit, onConfirm: (String, String, String, String) -> Unit) {
    val nombreFiscal = remember { mutableStateOf("") }
    val nombreComercial = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }
    val correo = remember { mutableStateOf("") }
    var informationMessage = remember { mutableStateOf("") }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo Cliente") },
        text = {
            Column {
                TextField(
                    value = nombreFiscal.value,
                    onValueChange = {nombreFiscal.value = it},
                    placeholder = { Text(text = "Nombre fiscal") }
                )
                TextField(
                    value = nombreComercial.value,
                    onValueChange = {nombreComercial.value = it},
                    placeholder = { Text(text = "Nombre comercial") }
                )
                TextField(
                    value = telefono.value,
                    onValueChange = {telefono.value = it},
                    placeholder = { Text(text = "Teléfono") }
                )
                TextField(
                    value = correo.value,
                    onValueChange = {correo.value = it},
                    placeholder = { Text(text = "Correo electrónico") }
                )
                if (informationMessage.value.isNotEmpty()) {
                    Text(
                        text = informationMessage.value,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (nombreFiscal.value.isNotBlank() && nombreComercial.value.isNotBlank() &&
                        telefono.value.isNotBlank() && correo.value.isNotBlank()) {
                        onConfirm(nombreFiscal.value, nombreComercial.value, telefono.value, correo.value)
                        informationMessage = mutableStateOf("")  // Limpiar el mensaje de error
                        onDismiss() // Cerrar el diálogo
                    } else {
                        informationMessage.value = "Todos los campos son obligatorios"
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
