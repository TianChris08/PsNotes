package com.example.psnotes.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.psnotes.data.model.Material
import java.util.UUID

@Composable
fun NuevoMaterialDialog(
    onDismiss: () -> Unit,
    onAdd: (Material) -> Unit,
) {
    var materialName by remember { mutableStateOf("") }
    var tipoMaterial by remember { mutableStateOf("") }
    var estadoMaterial by remember { mutableStateOf("") }
    var precioUnitarioMaterial by remember { mutableStateOf("") }
    var especificacionesMaterial by remember { mutableStateOf("") }
    var fechaExpiracionMaterial by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Añadir Nuevo Material") },
        text = {
            LazyColumn {
                item {
                    TextField(
                        value = materialName,
                        onValueChange = { materialName = it },
                        label = { Text("Nombre del Material") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    TextField(
                        value = precioUnitarioMaterial,
                        onValueChange = { newValue ->
                            // Permitir solo números y un punto decimal
                            val filteredValue =
                                newValue.replace(",", ".") // Cambiar comas por puntos
                                    .filter { it.isDigit() || it == '.' }

                            // Asegurar solo un punto decimal
                            val parts = filteredValue.split('.')
                            val formattedValue = if (parts.size > 2) {
                                parts.first() + "." + parts.drop(1).first()
                            } else {
                                filteredValue
                            }

                            // Limitar a dos decimales
                            val decimalParts = formattedValue.split('.')
                            precioUnitarioMaterial = if (decimalParts.size > 1) {
                                decimalParts[0] + "." + decimalParts[1].take(2)
                            } else {
                                formattedValue
                            }
                        },
                        label = { Text("Precio Unitario") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    TextField(
                        value = especificacionesMaterial,
                        onValueChange = { especificacionesMaterial = it },
                        label = { Text("Especificaciones") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Crear material
                    val material = Material(
                        UUID.randomUUID().toString(),
                        materialName,
                        tipoMaterial,
                        estadoMaterial,
                        precioUnitarioMaterial.toDouble(),
                        especificacionesMaterial,
                        fechaExpiracionMaterial,
                        estadoMaterial
                    )
                    onAdd(material)
                }
            ) {
                Text("Añadir")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}