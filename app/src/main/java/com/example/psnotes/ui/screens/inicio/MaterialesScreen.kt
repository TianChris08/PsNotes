package com.example.psnotes.ui.screens.inicio

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
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
import com.example.psnotes.ui.components.NuevoMaterialDialog
import com.example.psnotes.ui.viewmodel.MaterialViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun MaterialesScreen(materialViewModel: MaterialViewModel) {
    // Observar los materiales del ViewModel
    val materialesState by remember { derivedStateOf { materialViewModel.materialesState } }
    val materialesSeleccionados by remember { derivedStateOf { materialViewModel.materialesSeleccionadosState } }

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

    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.Center)
        {
            Text("Materiales")
        }
        HorizontalDivider()
        /*Row {
            TextField(
                value = searchText,
                onValueChange = { text ->
                    searchText = text
                    expanded = true
                    // llamar al mét0do de búsqueda del ViewModel
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
                        },
                        text = { Text(material.nombre) },

                    )
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
        }*/

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
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .weight(0.3f),
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
            /*item {
                Button(
                    onClick = {
                        showDialog = true
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Crear Material",
                        color = colorScheme.onBackground
                        )
                }
            }*/
        }



        if (showDialog) {
            NuevoMaterialDialog(
                onDismiss = { showDialog = false },
                onAdd = { material ->
                    // Usar el ViewModel para insertar el material en la base de datos
                    materialViewModel.createMaterial(material)
                    selectedMaterials[material.nombre] = 0 // Añadir a la lista de seleccionados con cantidad 0
                    showDialog = false
                }
            )
        }
    }
}

