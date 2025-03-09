package com.example.psnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.psnotes.data.database.AppDatabase
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.repository.ClienteDao
import com.example.psnotes.data.repository.ClienteRepository
import com.example.psnotes.data.repository.DummyClienteDao
import com.example.psnotes.ui.screens.BottomBar
import com.example.psnotes.ui.screens.InicioScreen
import com.example.psnotes.ui.theme.PsNotesTheme
import com.example.psnotes.ui.viewmodel.ClienteViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(this, AppDatabase::class.java, "user_db").build()


        setContent {
            PsNotesTheme {
                val dao = db.clienteDao()
                val repository = ClienteRepository(dao)
                val viewModel: ClienteViewModel = ClienteViewModel(repository)
                val clientePrueba = Cliente(3,"Pepito Rodríguez", "Mercachona", "643622043", "cacatua22@gmail.com")
                //viewModel.agregarCliente(clientePrueba)

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
