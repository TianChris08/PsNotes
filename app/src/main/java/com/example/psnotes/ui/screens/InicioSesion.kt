package com.example.psnotes.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.example.psnotes.ui.viewmodel.TrabajadorViewModel

@Composable
fun InicioSesion(paddingValues: PaddingValues, viewModel: TrabajadorViewModel, navController: NavController) {

    var codigoTrabajador by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /*Button(onClick = { viewModel.createTrabajador(123456789, "Test", 10.0, 1234) }) {
            Text("Crear Trabajador de Prueba")
        }*/

        TextField(
            modifier = Modifier.padding(10.dp),
            value = codigoTrabajador,
            onValueChange = { codigoTrabajador = it },
            placeholder = { Text("Código de trabajador") }
        )

        TextField(
            modifier = Modifier.padding(10.dp),
            value = pin,
            onValueChange = { pin = it },
            placeholder = { Text("Pin de inicio sesión") }
        )

        Button(
            modifier = Modifier.padding(10.dp),
            onClick = {
                val codigoInt = codigoTrabajador.toIntOrNull() // Convertir el codigo a Int
                val pinInt = pin.toIntOrNull() // Convertir el pin a Int

                if (codigoInt != null && viewModel.state.trabajadores.any { /*it.codigoTrabajador == codigoInt &&*/ it.pin == pinInt}) {
                    navController.navigate("Inicio")
                } else {
                    error = true
                }
            }
        ) {
            Text("Iniciar sesión")
        }

        if (error) {
            Text("PIN incorrecto")
        }
    }
}