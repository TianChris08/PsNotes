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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.psnotes.R
import com.example.psnotes.data.AppDatabase
import com.example.psnotes.data.AppDatabaseSingleton
import com.example.psnotes.data.SessionManager
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.model.Trabajador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

@Composable
fun SplashScreen(
    context: Context,
    navController: NavController
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        //.padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = (if (isSystemInDarkTheme()) {
                    painterResource(id = R.drawable.pensisoft_logo_dark_theme)
                } else {
                    painterResource(id = R.drawable.pensisoft_logo_light_theme)
                }), contentDescription = "Logotipo de Pensisoft", contentScale = ContentScale.Inside
            )
            Text(
                text = "PsTask", fontSize = 30.sp, fontWeight = FontWeight.Bold
            )
        }

    }
    if (SessionManager.isLoggedIn(context)) {
        // Si está logueado, navegar a la pantalla principal
        navController.navigate("Inicio") {
            popUpTo("Splash") { inclusive = true }
        }
    } else {
        // Si no está logueado, navegar a la pantalla de login
        navController.navigate("InicioSesion") {
            popUpTo("Splash") { inclusive = true }
        }
    }

    LaunchedEffect(Unit) {
        context.deleteDatabase("ps_notes_database")
        val appDatabase = AppDatabaseSingleton.getDatabase(context)
        initializeData(appDatabase)

        // Después de inicializar los datos, navegar a la siguiente pantalla
        if (SessionManager.isLoggedIn(context)) {
            // Si está logueado, navegar a la pantalla principal
            navController.navigate("Inicio") {
                popUpTo("Splash") { inclusive = true }
            }
        } else {
            // Si no está logueado, navegar a la pantalla de login
            navController.navigate("InicioSesion") {
                popUpTo("Splash") { inclusive = true }
            }
        }
    }

}

private suspend fun initializeData(appDatabase: AppDatabase) {
    withContext(Dispatchers.IO) {
        // Insertar datos de ejemplo para cada tabla
        val trabajadorChris = Trabajador(
            id = UUID.randomUUID().toString(), nombre = "Chris", tarifa = 15.0, pin = 1234
        )
        appDatabase.trabajadorDAO.insertTrabajador(trabajadorChris)
        val trabajadorPol = Trabajador(
            id = UUID.randomUUID().toString(), nombre = "Pol", tarifa = 25.0, pin = 1234
        )
        appDatabase.trabajadorDAO.insertTrabajador(trabajadorPol)
        val trabajadorMarc = Trabajador(
            id = UUID.randomUUID().toString(), nombre = "Marc", tarifa = 25.0, pin = 1234
        )
        appDatabase.trabajadorDAO.insertTrabajador(trabajadorMarc)

        val cliente = Cliente(
            id = UUID.randomUUID().toString(),
            fiscalName = "Marc Pensi",
            commercialName = "Pensisoft",
            telefono = "345345345",
            correo = "marcpensi@gmail.com",
            coordenadasNegocio = "39.98096459203265, -0.028817101095514178"
        )
        appDatabase.clienteDao.insertClient(cliente)

        /*val nota = Nota(
            id = UUID.randomUUID().toString(),
            personaContacto = "John Doe",
            clienteId = "cliente1",
            trabajadorId = "trabajador1",
            notaCerradaEn = "2025-03-01",
            fecha = "2025-02-28",
            observacionesPrivadas = "Observaciones privadas",
            observacionesPublias = "Se portó mal",
            trabajoRealizado = "trabajo realizado",
            firma = "firmado"
        )
        appDatabase.notaDAO.insertNota(nota)*/
    }
}