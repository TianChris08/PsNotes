package com.example.psnotes.ui.screens

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.repository.ClienteDAO
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun MapScreen(context : Context, paddingValues: PaddingValues, dao: ClienteDAO) {
    // Obtener lista de clientes desde la base de datos
    val clientes = remember { mutableStateOf(emptyList<Cliente>()) }
    val ubicacionActual = remember { mutableStateOf<LatLng?>(null) }

    // Ejecutar la consulta en un hilo de fondo
    LaunchedEffect(Unit) {
        clientes.value = withContext(Dispatchers.IO) {
            dao.getTodosClientes() // Asegúrate de que este método existe en tu DAO
        }
        // Obtener ubicación actual
        getLastKnownLocation(context) {
            ubicacionActual.value = it
        }
    }

    GoogleMap(
        modifier = Modifier.padding(paddingValues),
        properties = MapProperties(isMyLocationEnabled = true)
    ) {
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
                Circle(
                    center = latLng,
                    radius = 50.0,  // Radio en metros
                    fillColor = Color(0x999FFF85), // Rojo con transparencia
                    strokeColor = Color.Red, // Borde rojo
                    strokeWidth = 2f // Grosor del borde
                )

                ubicacionActual.value?.let {
                    if (isWithinRadius(it, latLng)) {
                        // ⚡ Aquí haces la acción que quieras
                        Log.d("Mapa", "¡Estás dentro del área de ${cliente.commercialName}!")
                        // También puedes usar efectos como:
                        // Toast.makeText(context, "Estás en el rango de ${cliente.commercialName}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

fun isWithinRadius(
    currentLatLng: LatLng,
    targetLatLng: LatLng,
    radiusInMeters: Float = 50f
): Boolean {
    val result = FloatArray(1)
    Location.distanceBetween(
        currentLatLng.latitude,
        currentLatLng.longitude,
        targetLatLng.latitude,
        targetLatLng.longitude,
        result
    )
    return result[0] <= radiusInMeters
}

fun getLastKnownLocation(
    context: Context,
    onLocationFound: (LatLng?) -> Unit
) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    if (ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        onLocationFound(null) // No hay permiso
        return
    }

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            location?.let {
                onLocationFound(LatLng(it.latitude, it.longitude))
            } ?: onLocationFound(null)
        }
        .addOnFailureListener {
            onLocationFound(null)
        }
}
