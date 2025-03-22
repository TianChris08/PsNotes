package com.example.psnotes

import android.annotation.SuppressLint
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.psnotes.data.database.ClienteTable
import com.example.psnotes.data.database.MaterialTable
import com.example.psnotes.data.database.TrabajadorTable
import com.example.psnotes.data.model.Trabajador
import com.example.psnotes.ui.components.BottomBar
import com.example.psnotes.ui.screens.InicioScreen
import com.example.psnotes.ui.screens.InicioSesion
import com.example.psnotes.ui.screens.MapScreen
import com.example.psnotes.ui.screens.NotasScreen
import com.example.psnotes.ui.screens.PerfilScreen
import com.example.psnotes.ui.screens.SplashScreen
import com.example.psnotes.ui.theme.PsNotesTheme
import com.example.psnotes.ui.viewmodel.ClienteViewModel
import com.example.psnotes.ui.viewmodel.TrabajadorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PsNotesTheme {
                val context = LocalContext.current
                val navController = rememberNavController()

                val navBackStackEntry = navController.currentBackStackEntryAsState().value
                val currentRoute = navBackStackEntry?.destination?.route

                /*
                context.deleteDatabase("Nota")
                context.deleteDatabase("trabajadores_db")
                context.deleteDatabase("materiales_db")
                context.deleteDatabase("clientes_db")
                */

                // BASE DE DATOS TRABAJADORES
                val dbTrabajadores = Room.databaseBuilder(this, TrabajadorTable::class.java, "trabajadores_db").build()
                val trabajadorDao = dbTrabajadores.trabajadorDAO
                val trabajador = Trabajador(
                    id = UUID.randomUUID().toString(),
                    nombre = "Chris",
                    tarifa = 25.0,
                    pin = 1234
                )
                CoroutineScope(Dispatchers.IO).launch {
                        // Realiza tu operación de base de datos aquí
                    trabajadorDao.insertTrabajador(trabajador) // Esto es solo un ejemplo

                }

                val viewModelTrabajador by viewModels<TrabajadorViewModel>(factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return TrabajadorViewModel(trabajadorDao) as T
                        }
                    }
                })

                // BASE DE DATOS MATERIALES(TODO)
                val dbMateriales = Room.databaseBuilder(this, MaterialTable::class.java, "materiales_db").build()
                val MaterialesDao = dbMateriales.materialDao



                // BASE DE DATOS DE CLIENTES
                val dbClientes = Room.databaseBuilder(this, ClienteTable::class.java, "clientes_db").build()
                val clienteDao = dbClientes.clienteDao
                val viewModelCliente by viewModels<ClienteViewModel>(factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return ClienteViewModel(clienteDao) as T
                        }
                    }
                })



                Scaffold(
                    bottomBar = {
                        if (currentRoute != "Splash" && currentRoute != "InicioSesion") {
                            BottomBar(navController, currentRoute.toString())
                        }
                    }) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "Splash",
                    ) {
                        composable("Splash") {
                            SplashScreen(context,navController)
                        }
                        composable("InicioSesion") {
                            InicioSesion(context, paddingValues, viewModelTrabajador, navController)
                        }
                        composable("Inicio") {
                            InicioScreen(paddingValues, viewModelCliente)
                        }
                        composable("Buscar") {
                            MapScreen(paddingValues, context, clienteDao)
                        }
                        composable("Notas") {
                            NotasScreen(paddingValues, navController, null)
                        }
                        composable("Perfil") {
                            PerfilScreen(context, navController)
                        }
                        composable("Ajustes") {
                            //AjustesScreen(paddingValues, navController)
                        }
                    }
                }
            }
        }
    }
}
