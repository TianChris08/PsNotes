package com.example.psnotes.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.repository.ClienteDAO
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun MapScreen(paddingValues: PaddingValues, dao: ClienteDAO) {
    // Obtener lista de clientes desde la base de datos
    val clientes = remember { mutableStateOf(emptyList<Cliente>()) }

    // Ejecutar la consulta en un hilo de fondo
    LaunchedEffect(Unit) {
        clientes.value = withContext(Dispatchers.IO) {
            dao.getTodosClientes() // Asegúrate de que este método existe en tu DAO
        }
    }

    GoogleMap(modifier = Modifier.padding(paddingValues)) {
        clientes.value.forEach { cliente ->
            val coords = cliente.coordenadasNegocio?.split(",")?.map { it.toDoubleOrNull() }
            if (coords?.size == 2 && coords.all { it != null }) {
                val latLng = LatLng(coords[0]!!, coords[1]!!)

                val markerState = rememberMarkerState(position = latLng)

                Marker(
                    state = markerState,
                    title = "Cliente ${cliente.commercialName}",
                    snippet = "Ubicación: ${coords[0]}, ${coords[1]}"
                )
            }
        }
    }
}