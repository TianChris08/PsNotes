package com.example.psnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.psnotes.ui.screens.BottomBar
import com.example.psnotes.ui.screens.InicioScreen
import com.example.psnotes.ui.theme.PsNotesTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PsNotesTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomBar(navController) }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "inicio",
                    ) {
                        composable("Inicio") {
                            InicioScreen(paddingValues)
                        }
                        composable("Buscar") {
                            //InicioScreen(paddingValues, navController)
                        }
                        composable("Favoritos") {
                            //InicioScreen(paddingValues, navController)
                        }
                        composable("Perfil") {
                            //InicioScreen(paddingValues, navController)
                        }
                        composable("Ajustes") {
                            //InicioScreen(paddingValues, navController)
                        }
                    }
                }
            }
        }
    }
}


// 🔹 Pantallas Inferiores (Composables)

@Composable
fun DefaultScreen() {
    Text("Selecciona una sección")
}
