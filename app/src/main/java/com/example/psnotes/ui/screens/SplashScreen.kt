package com.example.psnotes.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.psnotes.R
import com.example.psnotes.data.AppDatabase
import com.example.psnotes.data.AppDatabaseSingleton
import com.example.psnotes.data.SessionManager
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.model.Nota
import com.example.psnotes.data.model.Trabajador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.UUID

@Composable
fun SplashScreen(context: Context, navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        //.padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter =
                    (if (isSystemInDarkTheme()) {
                        painterResource(id = R.drawable.pensisoft_logo_dark_theme)
                    } else {
                        painterResource(id = R.drawable.pensisoft_logo_light_theme)
                    }),
                contentDescription = "Logotipo de Pensisoft",
                contentScale = ContentScale.Inside
            )
            Text(
                text = "PsTask",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
    LaunchedEffect(Unit) {

        val appDatabase = AppDatabaseSingleton.getDatabase(context)

        // Insertar datos de ejemplo en las tablas
        initializeData(appDatabase)

        navController.popBackStack()
        if (SessionManager.isLoggedIn(context)) {
            // Si está logueado, navegar a la pantalla principal
            navController.navigate("Inicio")
        } else {
            // Si no está logueado, navegar a la pantalla de login
            navController.navigate("InicioSesion")
        }
    }

}

private suspend fun initializeData(appDatabase: AppDatabase) {
    withContext(Dispatchers.IO) {
        // Insertar datos de ejemplo para cada tabla
        val trabajador = Trabajador(
            id = UUID.randomUUID().toString(),
            nombre = "Chris",
            tarifa = 25.0,
            pin = 1234
        )
        appDatabase.trabajadorDAO.insertTrabajador(trabajador)

        val cliente = Cliente(
            id = UUID.randomUUID().toString(),
            fiscalName = "fiscal",
            commercialName = "comercial",
            telefono = "345345345",
            correo = "345@gmail.com",
            coordenadasNegocio = "39.9946, -0.0690"
        )
        appDatabase.clienteDao.insertClient(cliente)

        val nota = Nota(
            id = UUID.randomUUID().toString(),
            personaContacto = "John Doe",
            clienteId = "cliente1",
            trabajadorId = "trabajador1",
            notaCerradaEn = "2025-03-01",
            fecha = "2025-02-28",
            observacionesPrivadas = "Observaciones privadas",
            observacionesPublias = "Se portó mal",
            firma = "firmado"
        )
        appDatabase.notaDAO.insertNota(nota)
    }
}