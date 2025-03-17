package com.example.psnotes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cable
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.psnotes.ui.components.MiDesplegable
import com.example.psnotes.ui.components.PermissionScreen
import com.example.psnotes.ui.screens.inicio.FirmaScreen
import com.example.psnotes.ui.screens.inicio.MaterialesScreen
import com.example.psnotes.ui.screens.inicio.Observaciones1Screen
import com.example.psnotes.ui.screens.inicio.Observaciones2Screen
import com.example.psnotes.ui.screens.inicio.TrabajoScreen
import com.example.psnotes.ui.viewmodel.ClienteViewModel
import com.example.psnotes.ui.viewmodel.PermissionViewModel
import com.google.android.libraries.places.api.Places


@Composable
fun InicioScreen(
    paddingValues: PaddingValues,
    viewModel: ClienteViewModel,
) {
    val context = LocalContext.current
    val permissionViewModel: PermissionViewModel = viewModel()

    val currentPermission = remember { mutableStateOf("location") }
    val permissionState = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        permissionViewModel.checkLocationPermission(context)
        permissionViewModel.checkStoragePermission(context)
        permissionViewModel.checkInternetPermission()
    }

    val locationPermissionGranted by permissionViewModel.locationPermissionGranted.observeAsState(false)
    val storagePermissionGranted by permissionViewModel.storagePermissionGranted.observeAsState(false)

    LaunchedEffect(currentPermission.value) {
        when (currentPermission.value) {
            "location" -> {
                if (locationPermissionGranted) {
                    currentPermission.value = "storage"
                }
            }
            "storage" -> {
                if (storagePermissionGranted) {
                    currentPermission.value = "internet"
                }
            }
        }
    }

    // Estado para controlar qué vista mostrar en la parte inferior
    val selectedSection = remember { mutableStateOf("trabajo") }
    val showDialog = remember { mutableStateOf(false) } // Estado para mostrar el diálogo


    if (!permissionState.value) {
        PermissionScreen(permissionViewModel)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {


            if (!Places.isInitialized()) {
                Places.initialize(context, "AIzaSyAmqr5VECPpGR-vlAo3mGqn60Dg24I9pV8")
            }

            val permissionViewModel: PermissionViewModel = viewModel()

            // Verificar los permisos al inicio
            LaunchedEffect(Unit) {
                permissionViewModel.checkLocationPermission(context)
                permissionViewModel.checkStoragePermission(context)
                permissionViewModel.checkInternetPermission()
            }

            // Observar los cambios de los permisos utilizando LiveData y observeAsState
            val locationPermissionGranted by permissionViewModel.locationPermissionGranted.observeAsState(
                false
            )
            val storagePermissionGranted by permissionViewModel.storagePermissionGranted.observeAsState(
                false
            )

            // Condicional para mostrar la UI según los permisos
            if (locationPermissionGranted && storagePermissionGranted) {
                // Mostrar la UI principal
                Text("Bienvenido a la app")
            } else {
                // Mostrar pantalla de permisos
                PermissionScreen(permissionViewModel)
            }


            // 🔹 Parte Superior - Información fija
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
                    .border(width = 1.dp, color = colorScheme.onBackground)
            ) {
                Text("Información Principal", style = MaterialTheme.typography.titleMedium)
                HorizontalDivider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    MiDesplegable(viewModel = viewModel)
                    IconButton(
                        onClick = {
                            showDialog.value = true
                        },
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .background(colorScheme.onBackground)
                    ) {
                        Icon(
                            Icons.Outlined.Add,
                            contentDescription = "Agregar cliente",
                            tint = colorScheme.background
                        )
                    }
                }
            }
            if (showDialog.value) {
                NuevoClienteForm(
                    onDismiss = { showDialog.value = false },
                    onConfirm = { f1, f2, f3, f4 ->
                        viewModel.createClient(f1, f2, f3, f4)
                    }
                )
            }

            // 🔹 Parte Inferior - Contenido Dinámico
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.7f),
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f) // Toma la mitad del espacio
                ) {
                    when (selectedSection.value) {
                        "trabajo" -> TrabajoScreen()
                        "materiales" -> MaterialesScreen()
                        "firma" -> FirmaScreen()
                        "observaciones1" -> Observaciones1Screen()
                        "observaciones2" -> Observaciones2Screen()
                    }
                }

                // 🔹 Botones de Cambio
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    val shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)

                    listOf(
                        Icons.Filled.Work to "trabajo",
                        Icons.Filled.Cable to "materiales",
                        Icons.Filled.TextFormat to "firma",
                        Icons.Filled.NoteAlt to "observaciones1",
                        Icons.Outlined.NoteAlt to "observaciones2"
                    ).forEach { (icon, text) ->
                        FloatingActionButton(
                            onClick = { selectedSection.value = text },
                            shape = shape,
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                        ) {
                            Icon(icon, contentDescription = text)
                        }
                    }
                }
            }
        }
    }
}