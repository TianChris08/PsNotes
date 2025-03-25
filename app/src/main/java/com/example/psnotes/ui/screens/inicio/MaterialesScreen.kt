package com.example.psnotes.ui.screens.inicio

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.psnotes.data.model.Material
import com.example.psnotes.ui.viewmodel.MaterialViewModel
import java.util.UUID

@SuppressLint("MutableCollectionMutableState")
@Composable
fun MaterialesScreen(materialViewModel: MaterialViewModel) {
    // Observar los materiales del ViewModel
    val materialesState by remember { derivedStateOf { materialViewModel.state.materiales } }
    val materialesSeleccionados by remember { derivedStateOf { materialViewModel.state.materialesSeleccionados } }

    var searchText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    var selectedMaterials by remember { mutableStateOf(mutableMapOf<String, Int>()) }

    // Filtrar materiales basado en el texto de búsqueda
    val filteredMateriales by remember(searchText, materialesState) {
        derivedStateOf {
            materialesState.filter { material ->
                material.nombre.contains(searchText, ignoreCase = true) ||
                material.tipo?.contains(searchText, ignoreCase = true) == true ||
                material.especificaciones?.contains(searchText, ignoreCase = true) == true
            }
        }
    }

    Column(modifier = Modifier.padding(8.dp)) {
        Row {
            TextField(
                value = searchText,
                onValueChange = { text ->
                    searchText = text
                    expanded = true
                    // Opcional: llamar al mét0do de búsqueda del ViewModel
                    materialViewModel.searchMateriales(text)
                },
                label = { Text("Buscar Material") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f)
                    .clickable { expanded = true },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        materialViewModel.searchMateriales(searchText)
                    }
                ),
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = {
                            searchText = ""
                            expanded = false
                        }) {
                            Icon(Icons.Default.Clear, contentDescription = "Limpiar búsqueda")
                        }
                    }
                }
            )

            // Dropdown de materiales
            DropdownMenu(
                expanded = expanded && filteredMateriales.isNotEmpty(),
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                filteredMateriales.forEach { material ->
                    DropdownMenuItem(
                        onClick = {
                            searchText = material.nombre
                            expanded = false

                            // Añadir a materiales seleccionados si no existe
                            if (!selectedMaterials.containsKey(material.nombre)) {
                                selectedMaterials[material.nombre] = 0
                            }
                        }
                    ) {
                        Text(material.nombre)
                    }
                }
            }

            // Botón de añadir
            Button(
                onClick = {
                    if (searchText.isNotEmpty()) {
                        val materialToAdd = filteredMateriales.firstOrNull { it.nombre == searchText }
                        if (materialToAdd != null && !materialesSeleccionados.containsKey(materialToAdd.nombre)) {
                            materialViewModel.addMaterialSeleccionado(materialToAdd.nombre)
                        }
                    }
                },
                modifier = Modifier.weight(0.15f)
            ) { Text("+") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de materiales seleccionados con sus cantidades
        LazyColumn {
            itemsIndexed(materialesSeleccionados.toList()) { index, (material, quantity) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = colorScheme.background)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = material,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            // Contador de cantidad
                            IconButton(onClick = {
                                if (quantity > 0) materialViewModel.updateMaterialQuantity(material, quantity - 1)
                            }) {
                                Icon(Icons.Default.Remove, contentDescription = "Disminuir cantidad")
                            }
                            Text(
                                text = "$quantity",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            IconButton(onClick = {
                                materialViewModel.updateMaterialQuantity(material, quantity + 1)
                            }) {
                                Icon(Icons.Default.Add, contentDescription = "Aumentar cantidad")
                            }


                            // Precio del material
                            Text(
                                text = "%.2f €".format(materialViewModel.calcularPrecioSegunCantidad(
                                    nombre = material,
                                    cantidad = quantity
                                )),
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                modifier = Modifier.padding(horizontal = 8.dp).weight(0.3f),
                                textAlign = TextAlign.Center
                            )

                            // Botón para eliminar el material
                            IconButton(
                                onClick = { materialViewModel.removeMaterialSeleccionado(material) },
                                modifier = Modifier.weight(0.2f)) {
                                Icon(Icons.Default.Clear, contentDescription = "Eliminar material")
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                showDialog = true
            }, modifier = Modifier.align(Alignment.CenterHorizontally)
        ) { Text("Crear Material") }

        if (showDialog) {
            AddMaterialDialog(
                onDismiss = { showDialog = false },
                onAdd = { material ->
                    // Usar el ViewModel para insertar el material en la base de datos
                    materialViewModel.createMaterial(material)
                    selectedMaterials[material.nombre] = 0 // Añadir a la lista de seleccionados con cantidad 0
                    showDialog = false
                },
                materialViewModel = materialViewModel
            )
        }
    }
}

@Composable
fun AddMaterialDialog(
    onDismiss: () -> Unit,
    onAdd: (Material) -> Unit,
    materialViewModel: MaterialViewModel
) {
    var materialName by remember { mutableStateOf("") }
    var tipoMaterial by remember { mutableStateOf("") }
    var estadoMaterial by remember { mutableStateOf("") }
    var precioUnitarioMaterial by remember { mutableStateOf("") }
    var especificacionesMaterial by remember { mutableStateOf("") }
    var fechaExpiracionMaterial by remember { mutableStateOf("") }
    val tipos = listOf("Tipo1", "Tipo2", "Tipo3") // Enum con tipos
    val estados = listOf("Nuevo", "Usado", "Dañado") // Enum con estados

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
                /*item {
                    DropdownMenu(
                        expanded = true,
                        onDismissRequest = { /* Implementar el cierre del desplegable */ }
                    ) {
                        tipos.forEach { tipo ->
                            DropdownMenuItem(onClick = { tipoMaterial = tipo }) {
                                Text(tipo)
                            }
                        }
                    }
                }*/
                /*item {
                    DropdownMenu(
                        expanded = true,
                        onDismissRequest = { /* Implementar el cierre del desplegable */ }
                    ) {
                        estados.forEach { estado ->
                            DropdownMenuItem(onClick = { estadoMaterial = estado }) {
                                Text(estado)
                            }
                        }
                    }
                }*/
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
                item {
                    TextField(
                        value = fechaExpiracionMaterial,
                        onValueChange = { fechaExpiracionMaterial = it },
                        label = { Text("Fecha de Expiración") },
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