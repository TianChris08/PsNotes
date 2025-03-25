package com.example.psnotes.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Router
import androidx.compose.material.icons.outlined.TextFormat
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.psnotes.ui.components.MiDesplegable
import com.example.psnotes.ui.components.NuevoClienteForm
import com.example.psnotes.ui.components.PermissionScreen
import com.example.psnotes.ui.screens.inicio.FirmaScreen
import com.example.psnotes.ui.screens.inicio.MaterialesScreen
import com.example.psnotes.ui.screens.inicio.Observaciones1Screen
import com.example.psnotes.ui.screens.inicio.Observaciones2Screen
import com.example.psnotes.ui.screens.inicio.TrabajoScreen
import com.example.psnotes.ui.viewmodel.ClienteViewModel
import com.example.psnotes.ui.viewmodel.MaterialViewModel
import com.example.psnotes.ui.viewmodel.NotaViewModel
import com.example.psnotes.ui.viewmodel.PermissionViewModel
import com.example.psnotes.ui.viewmodel.TrabajoViewModel
import com.google.android.libraries.places.api.Places


@Composable
fun InicioScreen(
    paddingValues: PaddingValues,
    viewModelMaterial: MaterialViewModel,
    clienteViewModel: ClienteViewModel
) {
    val context = LocalContext.current

    val trabajoViewModel: TrabajoViewModel = viewModel()
    val permissionViewModel: PermissionViewModel = viewModel()



    val precioTodosMateriales by viewModelMaterial.precioMateriales.collectAsState()
    val precioManoDeObra by trabajoViewModel.precioManoDeObra.collectAsState()

    val selectedSection = remember { mutableStateOf("trabajo") }
    val mostrarFormularioNuevoCliente = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (!Places.isInitialized()) {
            Places.initialize(context, "AIzaSyAmqr5VECPpGR-vlAo3mGqn60Dg24I9pV8")
        }

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
        if (!locationPermissionGranted || !storagePermissionGranted) {
            // Mostrar pantalla de permisos
            PermissionScreen(permissionViewModel)
        }

        // 🔹 Parte Superior - Información fija
        Row(modifier = Modifier
            .fillMaxSize()
            .weight(0.20f)
            .background(colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Información Principal",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = colorScheme.onBackground
                )
                HorizontalDivider(color = colorScheme.background)
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    MiDesplegable(
                        viewModel = clienteViewModel
                    )

                    IconButton(
                        onClick = {
                            mostrarFormularioNuevoCliente.value = true
                        },
                        colors = IconButtonColors(
                            containerColor = colorScheme.primary,
                            contentColor = colorScheme.onBackground,
                            disabledContainerColor = Color.DarkGray,
                            disabledContentColor = Color.Gray
                        ),
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clip(RoundedCornerShape(60.dp))
                    ) {
                        Icon(
                            Icons.Outlined.Add,
                            contentDescription = "Agregar cliente",
                            tint = colorScheme.onPrimaryContainer
                        )
                    }

                }
            }
        }

        if (mostrarFormularioNuevoCliente.value) {
            NuevoClienteForm(
                onDismiss = { mostrarFormularioNuevoCliente.value = false }, clienteViewModel = clienteViewModel
            )
        }

        HorizontalDivider(color = colorScheme.secondary)

        // 🔹 Parte Inferior - Contenido Dinámico
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.65f),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(colorScheme.background)
            ) {
                when (selectedSection.value) {
                    "trabajo" -> TrabajoScreen(trabajoViewModel)
                    "materiales" -> MaterialesScreen(viewModelMaterial)
                    "firma" -> FirmaScreen()
                    "observaciones1" -> Observaciones1Screen()
                    "observaciones2" -> Observaciones2Screen()
                }
            }

            // 🔹 Botones de Cambio
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                //val shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)

                listOf(
                    Icons.Outlined.WorkOutline to "trabajo",
                    Icons.Outlined.Router to "materiales",
                    Icons.Outlined.TextFormat to "firma",
                    Icons.Outlined.NoteAlt to "observaciones1",
                    Icons.Outlined.NoteAlt to "observaciones2"
                ).forEach { (icon, text) ->
                    FloatingActionButton(
                        onClick = { selectedSection.value = text },
                        //shape = shape,
                        modifier = Modifier.padding(vertical = 4.dp),
                        contentColor = colorScheme.onBackground,
                        containerColor = colorScheme.primary
                    ) {
                        Icon(icon, contentDescription = text)
                    }
                }
            }
        }

        HorizontalDivider(color = colorScheme.secondary)

        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.25f)
                .background(colorScheme.surface)
        ) {
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Row(horizontalArrangement = Arrangement.Center) {
                    Text(
                        "Mano de obra: %.2f €   \nMateriales: %.2f €   \nTotal: %.2f €".format(
                            precioManoDeObra,
                            viewModelMaterial.sumarPrecioMateriales(),
                            precioManoDeObra + viewModelMaterial.sumarPrecioMateriales()
                        ),
                        color = colorScheme.onBackground
                    )
                }
                Button(
                    colors = ButtonColors(
                        containerColor = colorScheme.tertiary,
                        contentColor = colorScheme.onBackground,
                        disabledContainerColor = colorScheme.surfaceVariant,
                        disabledContentColor = colorScheme.onSurfaceVariant
                    ),
                    onClick = { /*TODO(Crear nota con los datos seleccionados)*/ },
                ) {
                    Text(
                        text = "Guardar Nota",
                        color = colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}