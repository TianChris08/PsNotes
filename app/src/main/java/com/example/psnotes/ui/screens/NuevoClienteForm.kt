package com.example.psnotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.psnotes.ui.viewmodel.ClienteViewModel
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest

@Composable
fun NuevoClienteForm(onDismiss: () -> Unit, clienteViewModel: ClienteViewModel) {
    val context = LocalContext.current
    val placesClient = remember { Places.createClient(context) }

    val nombreFiscal = remember { mutableStateOf("") }
    val nombreComercial = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }
    val correo = remember { mutableStateOf("") }
    val direccion = remember { mutableStateOf("") }
    val coordenadas = remember { mutableStateOf("") }
    val sugerencias = remember { mutableStateOf(listOf<AutocompletePrediction>()) }
    val informationMessage = remember { mutableStateOf("") }
    val validationErrorMessages = remember { mutableStateOf<Map<String, String?>>(emptyMap()) }



    fun fetchAddressPredictions(query: String) {
        if (query.isNotEmpty()) {
            val request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .build()

            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    sugerencias.value = response.autocompletePredictions
                }
                .addOnFailureListener {
                    sugerencias.value = emptyList()
                }
        } else {
            sugerencias.value = emptyList()
        }
    }

    fun fetchPlaceDetails(placeId: String) {
        val request = FetchPlaceRequest.newInstance(placeId, listOf(Place.Field.LAT_LNG))

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                val place = response.place
                val latLng = place.latLng
                if (latLng != null) {
                    coordenadas.value = "${latLng.latitude}, ${latLng.longitude}"
                }
            }
            .addOnFailureListener {
                coordenadas.value = "No se pudo obtener coordenadas"
            }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo Cliente") },
        text = {
            Column {
                TextField(value = nombreFiscal.value, onValueChange = { nombreFiscal.value = it }, placeholder = { Text("Nombre fiscal") })
                validationErrorMessages.value["nombreFiscalBlank"]?.let { error ->
                    Text(error, color = Color.Red, modifier = Modifier.padding(top = 4.dp))
                }

                TextField(value = nombreComercial.value, onValueChange = { nombreComercial.value = it }, placeholder = { Text("Nombre comercial") })
                validationErrorMessages.value["nombreComercialBlank"]?.let { error ->
                    Text(error, color = Color.Red, modifier = Modifier.padding(top = 4.dp))
                }

                TextField(value = telefono.value, onValueChange = { telefono.value = it }, placeholder = { Text("Teléfono") })
                validationErrorMessages.value["telefonoBlank"]?.let { error ->
                    Text(error, color = Color.Red, modifier = Modifier.padding(top = 4.dp))
                }
                validationErrorMessages.value["telefonoValid"]?.let { error ->
                    Text(error, color = Color.Red, modifier = Modifier.padding(top = 4.dp))
                }

                TextField(value = correo.value, onValueChange = { correo.value = it }, placeholder = { Text("Correo electrónico") })
                validationErrorMessages.value["correoBlank"]?.let { error ->
                    Text(error, color = Color.Red, modifier = Modifier.padding(top = 4.dp))
                }
                validationErrorMessages.value["correoValid"]?.let { error ->
                    Text(error, color = Color.Red, modifier = Modifier.padding(top = 4.dp))
                }

                // Dirección con sugerencias
                TextField(
                    value = direccion.value,
                    onValueChange = {
                        direccion.value = it
                        fetchAddressPredictions(it)
                    },
                    placeholder = { Text("Dirección") }
                )

                sugerencias.value.forEach { sugerencia ->
                    Button(onClick = {
                        direccion.value = sugerencia.getFullText(null).toString()
                        fetchPlaceDetails(sugerencia.placeId) // Obtener coordenadas
                        sugerencias.value = emptyList()
                    }) {
                        Text(sugerencia.getFullText(null).toString())
                    }
                }

                if (coordenadas.value.isNotEmpty()) {
                    Text("Coordenadas: ${coordenadas.value}", color = colorScheme.onBackground, modifier = Modifier.padding(top = 8.dp))
                }

                if (informationMessage.value.isNotEmpty()) {
                    Text(text = informationMessage.value, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val errores = clienteViewModel.createClient(
                        nombreFiscal.value,
                        nombreComercial.value,
                        telefono.value,
                        correo.value,
                        if (coordenadas.value.isNotEmpty()) coordenadas.value else null
                    )

                    if (errores != null) {
                        // Actualizar los errores en la UI
                        validationErrorMessages.value = errores
                    } else {
                        // Cliente creado con éxito, cerrar el formulario
                        informationMessage.value = ""
                        onDismiss()
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}