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
import com.example.psnotes.data.database.ClienteTable
import com.example.psnotes.data.database.MaterialTable
import com.example.psnotes.data.database.TrabajadorTable
import com.example.psnotes.ui.components.BottomBar
import com.example.psnotes.ui.screens.InicioScreen
import com.example.psnotes.ui.screens.InicioSesion
import com.example.psnotes.ui.screens.MapScreen
import com.example.psnotes.ui.screens.NotasScreen
import com.example.psnotes.ui.theme.PsNotesTheme
import com.example.psnotes.ui.viewmodel.ClienteViewModel
import com.example.psnotes.ui.viewmodel.TrabajadorViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PsNotesTheme {
                LocalContext.current
                //context.deleteDatabase("trabajadores_db")

                val dbTrabajadores =
                    Room.databaseBuilder(this, TrabajadorTable::class.java, "trabajadores_db")
                        .build()
                val trabajadorDao = dbTrabajadores.trabajadorDAO
                val viewModelTrabajador by viewModels<TrabajadorViewModel>(factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return TrabajadorViewModel(trabajadorDao) as T
                        }
                    }
                })

                val dbMateriales =
                    Room.databaseBuilder(this, MaterialTable::class.java, "materiales_db").build()
                dbMateriales.materialDao

                val navController = rememberNavController()
                val db =
                    Room.databaseBuilder(this, ClienteTable::class.java, "clientes_db2").build()
                val dao = db.clienteDao
                val viewModelCliente by viewModels<ClienteViewModel>(factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return ClienteViewModel(dao) as T
                        }
                    }
                })

                /*LaunchedEffect(navController) {
                    navController.addOnDestinationChangedListener { _, destination, _ ->
                        currentRoute = destination.route ?: "InicioSesion"
                    }
                }*/

                Scaffold(
                    bottomBar = {
                        //if (currentRoute != "InicioSesion") {
                        BottomBar(navController)
                        //}
                    }) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "Inicio",
                    ) {
                        composable("InicioSesion") {
                            InicioSesion(paddingValues, viewModelTrabajador, navController)
                        }
                        composable("Inicio") {
                            InicioScreen(paddingValues, viewModelCliente)
                        }
                        composable("Buscar") {
                            MapScreen(paddingValues, navController)
                        }
                        composable("Notas") {
                            NotasScreen(paddingValues, navController)
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
