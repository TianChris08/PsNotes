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
import com.example.psnotes.data.SessionManager
import kotlinx.coroutines.delay

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
    LaunchedEffect(key1 = true) {
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