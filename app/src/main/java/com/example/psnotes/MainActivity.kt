package com.example.psnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.psnotes.data.database.ClienteDatabase
import com.example.psnotes.ui.screens.BottomBar
import com.example.psnotes.ui.screens.BuscarScreen
import com.example.psnotes.ui.screens.InicioScreen
import com.example.psnotes.ui.theme.PsNotesTheme
import com.example.psnotes.ui.viewmodel.ClienteViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PsNotesTheme {
                val context = LocalContext.current
                context.deleteDatabase("clientes_db")
                val navController = rememberNavController()
                val db = Room.databaseBuilder(this, ClienteDatabase::class.java, "clientes_db2").build()
                val dao = db.clienteDao
                val viewModel by viewModels<ClienteViewModel>(factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return ClienteViewModel(dao) as T
                        }
                    }
                })
                Scaffold(
                    bottomBar = { BottomBar(navController) }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "inicio",
                    ) {
                        composable("Inicio") {
                            InicioScreen(paddingValues, viewModel)
                        }
                        composable("Buscar") {
                            BuscarScreen(paddingValues, navController)
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
