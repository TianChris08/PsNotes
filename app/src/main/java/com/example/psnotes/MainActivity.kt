package com.example.psnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.psnotes.data.database.ClienteDatabase
import com.example.psnotes.ui.screens.HomeScreen
import com.example.psnotes.ui.theme.PsNotesTheme
import com.example.psnotes.ui.viewmodel.HomeViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        /*val db = Room.databaseBuilder(this, ClienteDatabase::class.java, "clients_db").build()
        val dao = db.clienteDao
        val repository = ClienteRepository(dao)
        //TODO: Crear un factory
        val viewModel = MainViewModel(repository)*/

        setContent {
            PsNotesTheme {
                /*val dao = db.clienteDao()
                val repository = ClienteRepository(dao)
                val viewModel: ClienteViewModel = ClienteViewModel(repository)
                val clientePrueba = Cliente(3,"Pepito Rodríguez", "Mercachona", "643622043", "cacatua22@gmail.com")
                //viewModel.agregarCliente(clientePrueba)*/

                /*val navController = rememberNavController()
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
                }*/
                //MainScreen(viewModel)
                val db = Room.databaseBuilder(this, ClienteDatabase::class.java, "clientes_db").build()
                val dao = db.clienteDao
                val viewModel by viewModels<HomeViewModel>(factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return HomeViewModel(dao) as T
                        }
                    }
                })
                HomeScreen(viewModel)
            }
        }
    }
}
