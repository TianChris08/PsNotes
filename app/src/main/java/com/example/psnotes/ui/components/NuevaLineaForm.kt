package com.example.psnotes.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.psnotes.ui.viewmodel.MaterialViewModel

@Composable
fun NuevaLineaForm(onDismiss: () -> Unit, materialesViewModel: MaterialViewModel) {
    val listaMateriales = materialesViewModel.listaTodosMateriales
    var nombreMaterial by remember { mutableStateOf("") }
    var unidadesMaterial by remember { mutableStateOf("") }
    val importe = calcularImporte(precioUnitarioMaterial.value.toDouble(), unidadesMaterial.toIntOrNull() ?: 0)
    val materialSeleccionado by remember {  }

    var listaMaterialesDesplegada by remember { mutableStateOf(materialesViewModel.listaMaterialesDesplegada) }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Crear nueva línea materiales") },
        text = {
            Column {
                Button(
                    onClick = { materialesViewModel.nuevoMaterialdesplegado = true }
                ) { Text("+") }
                Box {
                    TextField(
                        value = nombreMaterial,
                        onValueChange = { nombreMaterial = it },
                        label = { Text("Material") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState ->
                                materialesViewModel.listaMaterialesDesplegada =
                                    focusState.isFocused && listaMateriales.isNotEmpty()
                            },
                        trailingIcon = {
                            if (materialesViewModel.listaMaterialesDesplegada) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropUp,
                                    contentDescription = "Cerrar desplegable"
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Abrir desplegable"
                                )
                            }
                        }
                    )
                    if (materialesViewModel.listaMaterialesDesplegada) {
                        DropdownMenu(
                            expanded = materialesViewModel.listaMaterialesDesplegada,
                            onDismissRequest = { listaMaterialesDesplegada = false }
                        ) {
                            listaMateriales.forEach { material ->
                                DropdownMenuItem(
                                    text = { Text(material.nombre) },
                                    onClick = {
                                        nombreMaterial = material.nombre
                                        materialesViewModel.listaMaterialesDesplegada = false
                                    }
                                )
                            }
                        }
                    }
                }
                TextField(
                    value = unidadesMaterial,
                    onValueChange = { unidades: String ->
                        unidadesMaterial = unidades
                    },
                    label = { Text("Unidades") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
                TextField(
                    value = materialesViewModel.getPrecioMaterial(),
                    onValueChange = {precioUnitario ->
                        precioUnitarioMaterial = precioUnitario
                    },
                    label = { Text("Precio unitario") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
                TextField(
                    value = importe.toString(),
                    onValueChange = {  },
                    enabled = false,
                    label = { Text("Importe") }
                )
            }


            /*LazyColumn {
                items(listaMateriales.size) { index ->
                    val material = listaMateriales.get(index)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        onClick = {
                            materialesViewModel.addMaterialSeleccionado(material.nombre)
                            materialesViewModel.gestionarMaterialesdesplegado = false
                        }
                    ) {
                        Text(
                            text = "${material.nombre} | ${material.precioUnitario}",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }*/
        },
        confirmButton = {
            Button(
                onClick = {
                    materialesViewModel.addMaterialSeleccionado(nombreMaterial)
                    materialesViewModel.nuevaLineaForm = false
                }
            ) {
                Text("Añadir")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Salir") }
        }
    )

    if (materialesViewModel.nuevoMaterialdesplegado) {
        NuevoMaterialDialog(
            onDismiss = { materialesViewModel.nuevoMaterialdesplegado = false },
            onAdd = { material ->
                materialesViewModel.createMaterial(material)
                materialesViewModel.nuevoMaterialdesplegado = false
            }
        )
    }
}

fun calcularImporte(precioMaterial: Double, cantidadMaterial: Int): Double {
    return precioMaterial * cantidadMaterial
}