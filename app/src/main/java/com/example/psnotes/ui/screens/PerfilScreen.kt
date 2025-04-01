package com.example.psnotes.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.psnotes.data.SessionManager

@Composable
fun PerfilScreen(context: Context, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Perfil de usuario")

        Button(
            onClick = {
                // Limpiar los detalles de inicio de sesión
                SessionManager.clearSession(context)
                SessionManager.setLoggedIn(context, false)
                navController.popBackStack()
                navController.navigate("InicioSesion")
            }
        ) {
            Text("Cerrar sesión")
        }
    }
}