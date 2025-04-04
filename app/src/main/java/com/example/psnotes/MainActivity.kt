package com.example.psnotes

import android.os.Bundle
import android.util.Log
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
import com.example.psnotes.data.AppDatabaseSingleton
import com.example.psnotes.ui.components.BottomBar
import com.example.psnotes.ui.screens.InicioScreen
import com.example.psnotes.ui.screens.InicioSesion
import com.example.psnotes.ui.screens.MapScreen
import com.example.psnotes.ui.screens.NotasScreen
import com.example.psnotes.ui.screens.PerfilScreen
import com.example.psnotes.ui.screens.SplashScreen
import com.example.psnotes.ui.theme.PsNotesTheme
import com.example.psnotes.ui.viewmodel.ClienteViewModel
import com.example.psnotes.ui.viewmodel.MaterialViewModel
import com.example.psnotes.ui.viewmodel.NotaViewModel
import com.example.psnotes.ui.viewmodel.ObservacionesViewModel
import com.example.psnotes.ui.viewmodel.PermissionViewModel
import com.example.psnotes.ui.viewmodel.TrabajadorViewModel

class MainActivity : ComponentActivity() {
    lateinit var permissionViewModel: PermissionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as PsNotes
        val db = app.database

        enableEdgeToEdge()
        setContent {
            PsNotesTheme {
                val context = LocalContext.current
                val navController = rememberNavController()

                permissionViewModel = PermissionViewModel()

                // Llamas a los métodos del ViewModel y pasas la referencia a la actividad
                permissionViewModel.checkLocationPermission(this)
                permissionViewModel.checkStoragePermission(this)

                // Obtener los DAOs de la base de datos
                val clienteDao = db.clienteDao
                val notaDao = db.notaDAO
                val materialDao = db.materialDao
                val trabajadorDao = db.trabajadorDAO

                val navBackStackEntry = navController.currentBackStackEntryAsState().value
                val currentRoute = navBackStackEntry?.destination?.route

                val viewModelCliente by viewModels<ClienteViewModel>(factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return ClienteViewModel(clienteDao) as T
                        }
                    }
                })

                val viewModelNota by viewModels<NotaViewModel>(factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return NotaViewModel(notaDao) as T
                        }
                    }
                })

                val viewModelTrabajador by viewModels<TrabajadorViewModel>(factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return TrabajadorViewModel(trabajadorDao) as T
                        }
                    }
                })

                val viewModelMaterial by viewModels<MaterialViewModel>(factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return MaterialViewModel(materialDao) as T
                        }
                    }
                })

                val viewModelObservaciones by viewModels<ObservacionesViewModel>(factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return ObservacionesViewModel() as T
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
                            InicioSesion(
                                context, paddingValues, navController, viewModelTrabajador
                            )
                        }
                        composable("Inicio") {
                            InicioScreen(paddingValues, viewModelMaterial, viewModelCliente, viewModelNota, viewModelObservaciones, viewModelTrabajador, context)
                        }
                        composable("Buscar") {
                            MapScreen(context, paddingValues, clienteDao)
                        }
                        composable("Notas") {
                            NotasScreen(context, paddingValues, viewModelNota, viewModelCliente)
                        }
                        composable("Perfil") {
                            PerfilScreen(context, navController)
                        }
                        composable("Ajustes") {
                            //DrawBox(modifier = Modifier.fillMaxSize())
                            //AjustesScreen(paddingValues, navController)
                        }
                    }
                }
            }
        }
    }
}
