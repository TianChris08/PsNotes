package com.example.psnotes.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.psnotes.data.SessionManager
import com.example.psnotes.ui.viewmodel.TrabajadorViewModel

@Composable
fun InicioSesion(
    context: Context,
    paddingValues: PaddingValues,
    viewModel: TrabajadorViewModel,
    navController: NavController
) {

    var nombre by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }
    var recordarUsuario by remember { mutableStateOf(false) }


    // Verificar si hay datos guardados en SharedPreferences
    val (savedUsername, savedPin) = SessionManager.getSavedLoginDetails(context)

    if (recordarUsuario && savedUsername != null && savedPin != -1) {
        nombre = savedUsername
        pin = savedPin.toString()
        // Iniciar sesión automáticamente si los datos son correctos
        if (viewModel.state.trabajadores.any { it.nombre == nombre && it.pin == savedPin }) {
            SessionManager.setLoggedIn(context, true)
            navController.popBackStack()
            navController.navigate("Inicio")
        }
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            modifier = Modifier.padding(10.dp),
            value = nombre,
            onValueChange = { nombre = it },
            placeholder = { Text("Nombre del trabajador") })

        TextField(
            modifier = Modifier.padding(10.dp),
            value = pin,
            onValueChange = { pin = it },
            placeholder = { Text("Pin de inicio sesión") })

        Checkbox(
            checked = recordarUsuario, onCheckedChange = { recordarUsuario = it })
        Text("Iniciar sesión automáticamente")

        Button(
            modifier = Modifier.padding(10.dp), onClick = {
                val pinInt = pin.toIntOrNull()

                if (nombre != null && viewModel.state.trabajadores.any { it.nombre == nombre && it.pin == pinInt }) {
                    SessionManager.setLoggedIn(context, true)
                    if (recordarUsuario) {
                        SessionManager.saveLoginDetails(context, nombre, pinInt ?: -1)
                    } else {
                        // Borrar los datos de sesión si el checkbox no está marcado
                        SessionManager.clearSession(context)
                    }
                    navController.popBackStack()
                    navController.navigate("Inicio")
                } else {
                    error = true
                }
            }) {
            Text("Iniciar sesión")
        }

        if (error) {
            Text("PIN incorrecto")
        }
    }
}

