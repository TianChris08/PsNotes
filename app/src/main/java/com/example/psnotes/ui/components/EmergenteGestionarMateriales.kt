package com.example.psnotes.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.psnotes.ui.viewmodel.MaterialViewModel

@Composable
fun EmergenteGestionarMateriales(onDismiss: () -> Unit, materialesViewModel: MaterialViewModel) {
    val listaMateriales = materialesViewModel.materialesState

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Seleccionar materiales") },
        text = {
            LazyColumn {
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
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    materialesViewModel.nuevoMaterialdesplegado = true
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
