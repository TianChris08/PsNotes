package com.example.psnotes.ui.components

import androidx.compose.foundation.Image
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

@Composable
fun SplashScreen(navController: NavController) {

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
                painter = painterResource(id = R.drawable.pensisoft_logo_green),
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
        navController.navigate("Inicio")
    }

}