package com.example.psnotes.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.model.AutocompletePrediction

@Composable
fun NuevoClienteForm(onDismiss: () -> Unit, onConfirm: (String, String, String, String) -> Unit) {
    val context = LocalContext.current
    val placesClient = remember { Places.createClient(context) }

    val nombreFiscal = remember { mutableStateOf("") }
    val nombreComercial = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }
    val correo = remember { mutableStateOf("") }
    val direccion = remember { mutableStateOf("") }
    val sugerencias = remember { mutableStateOf(listOf<String>()) }
    val informationMessage = remember { mutableStateOf("") }

    // Verificar si Google Places API está inicializado
    if (!Places.isInitialized()) {
        Places.initialize(context, "YOUR_API_KEY")
    }

    // Verificación de permisos
    val isLocationPermissionGranted = ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    if (!isLocationPermissionGranted) {
        // Mostrar mensaje si no se tienen los permisos adecuados
        Toast.makeText(context, "Se requieren permisos para acceder a la ubicación", Toast.LENGTH_SHORT).show()
        return
    }

    // Función para obtener sugerencias de dirección
    fun fetchAddressPredictions(query: String) {
        if (query.isNotEmpty()) {
            val request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .build()

            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    val predictions = response.autocompletePredictions
                    sugerencias.value = predictions.map { it.getFullText(null).toString() }
                }
                .addOnFailureListener {
                    sugerencias.value = emptyList()
                }
        } else {
            sugerencias.value = emptyList()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo Cliente") },
        text = {
            Column {
                TextField(
                    value = nombreFiscal.value,
                    onValueChange = { nombreFiscal.value = it },
                    placeholder = { Text(text = "Nombre fiscal") }
                )
                TextField(
                    value = nombreComercial.value,
                    onValueChange = { nombreComercial.value = it },
                    placeholder = { Text(text = "Nombre comercial") }
                )
                TextField(
                    value = telefono.value,
                    onValueChange = { telefono.value = it },
                    placeholder = { Text(text = "Teléfono") }
                )
                TextField(
                    value = correo.value,
                    onValueChange = { correo.value = it },
                    placeholder = { Text(text = "Correo electrónico") }
                )

                // Campo de Dirección con Sugerencias
                TextField(
                    value = direccion.value,
                    onValueChange = {
                        direccion.value = it
                        fetchAddressPredictions(it) // Buscar direcciones al escribir
                    },
                    placeholder = { Text(text = "Dirección") }
                )

                sugerencias.value.forEach { sugerencia ->
                    Button(onClick = {
                        direccion.value = sugerencia
                        sugerencias.value = emptyList() // Limpiar las sugerencias
                    }) {
                        Text(sugerencia)
                    }
                }

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
                    if (nombreFiscal.value.isNotBlank() &&
                        nombreComercial.value.isNotBlank() &&
                        telefono.value.isNotBlank() &&
                        correo.value.isNotBlank()
                    ) {
                        onConfirm(nombreFiscal.value, nombreComercial.value, telefono.value, correo.value)
                        informationMessage.value = ""  // Limpiar el mensaje de error
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
