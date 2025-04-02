package com.example.psnotes.ui.screens

import android.content.Context
import androidx.compose.animation.core.animateIntAsState
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.psnotes.data.SessionManager
import com.example.psnotes.data.model.Nota
import com.example.psnotes.ui.components.NuevoClienteForm
import com.example.psnotes.ui.components.PermissionScreen
import com.example.psnotes.ui.components.miDesplegable
import com.example.psnotes.ui.components.showToast
import com.example.psnotes.ui.screens.inicio.FirmaScreen
import com.example.psnotes.ui.screens.inicio.MaterialesScreen
import com.example.psnotes.ui.screens.inicio.Observaciones1Screen
import com.example.psnotes.ui.screens.inicio.Observaciones2Screen
import com.example.psnotes.ui.screens.inicio.TrabajoScreen
import com.example.psnotes.ui.viewmodel.ClienteViewModel
import com.example.psnotes.ui.viewmodel.MaterialViewModel
import com.example.psnotes.ui.viewmodel.NotaViewModel
import com.example.psnotes.ui.viewmodel.ObservacionesViewModel
import com.example.psnotes.ui.viewmodel.PermissionViewModel
import com.example.psnotes.ui.viewmodel.TrabajadorViewModel
import com.example.psnotes.ui.viewmodel.TrabajoViewModel
import com.google.android.libraries.places.api.Places
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID


@Composable
fun InicioScreen(
    paddingValues: PaddingValues,
    viewModelMaterial: MaterialViewModel = viewModel(),
    clienteViewModel: ClienteViewModel = viewModel(),
    notaViewModel: NotaViewModel = viewModel(),
    observacionesViewModel: ObservacionesViewModel = viewModel(),
    trabajadorViewModel: TrabajadorViewModel = viewModel(),
    context: Context
) {
    val trabajoViewModel: TrabajoViewModel = viewModel()
    val permissionViewModel: PermissionViewModel = viewModel()

    var clienteId by remember { mutableStateOf("") }
    var personaContacto by remember { mutableStateOf("") }

    val precioMateriales = viewModelMaterial.sumarPrecioMateriales()
    val precioManoDeObra by trabajoViewModel.precioManoDeObra.collectAsState()

    val selectedSection = remember { mutableStateOf("trabajo") }
    val mostrarFormularioNuevoCliente = remember { mutableStateOf(false) }

    val observacionesPublicas by observacionesViewModel.observacionesPublicas.collectAsState()
    val observacionesPrivadas by observacionesViewModel.observacionesPrivadas.collectAsState()
    val trabajoRealizado by trabajoViewModel.trabajoRealizado

    val scaleTrabajos by animateIntAsState(if (selectedSection.value == "trabajo") 75 else 65)
    val scaleMateriales by animateIntAsState(if (selectedSection.value == "materiales") 75 else 65)
    val scaleFirma by animateIntAsState(if (selectedSection.value == "firma") 75 else 65)
    val scaleObservaciones1 by animateIntAsState(if (selectedSection.value == "observaciones1") 75 else 65)
    val scaleObservaciones2 by animateIntAsState(if (selectedSection.value == "observaciones2") 75 else 65)

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

        // 🔹 Parte Superior - Información Cliente
        Row(modifier = Modifier
            .fillMaxSize()
            .weight(0.25f)
            .background(colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                /*Text(
                    text = "Información Principal",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = colorScheme.onBackground
                )*/
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                        .weight(0.5f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    //Obtengo el id del cliente seleccionado
                    clienteId = miDesplegable(
                        modifier = Modifier.padding(top = 5.dp),
                        clienteViewModel = clienteViewModel
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
                            .padding(top = 5.dp, start = 8.dp)
                            .clip(RoundedCornerShape(60.dp))
                    ) {
                        Icon(
                            Icons.Outlined.Add,
                            contentDescription = "Agregar cliente",
                            tint = colorScheme.onPrimaryContainer
                        )
                    }
                }
                Row(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
                    .weight(0.5f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = personaContacto,
                        onValueChange = { personaContacto = it },
                        label = { Text("Persona de contacto") }
                    )
                }

            }
        }

        if (mostrarFormularioNuevoCliente.value) {
            NuevoClienteForm(
                onDismiss = { mostrarFormularioNuevoCliente.value = false },
                clienteViewModel = clienteViewModel
            )
        }

        val colorFondo = colorScheme.tertiary.copy(alpha = 0.5f)

        // 🔹 Parte Media - Contenido Dinámico
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.60f)
                .background(color = colorFondo, shape = RectangleShape)
            ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(12.dp)
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
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.End
            ) {
                listOf(
                    Icons.Outlined.WorkOutline to "trabajo",
                    Icons.Outlined.Router to "materiales",
                    Icons.Outlined.TextFormat to "firma",
                    Icons.Outlined.NoteAlt to "observaciones1",
                    Icons.Outlined.NoteAlt to "observaciones2"
                ).forEach { (icon, text) ->
                    FloatingActionButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .border(
                                width = 2.dp,
                                color = colorScheme.background,
                                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 0.dp, bottomEnd = 0.dp, bottomStart = 8.dp)
                            )
                            .width(
                                when (text) {
                                    "trabajo" -> scaleTrabajos.dp
                                    "materiales" -> scaleMateriales.dp
                                    "firma" -> scaleFirma.dp
                                    "observaciones1" -> scaleObservaciones1.dp
                                    else -> scaleObservaciones2.dp
                                }
                            ),
                        onClick = {
                            selectedSection.value = text
                        },
                        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 0.dp, bottomEnd = 0.dp, bottomStart = 8.dp),
                        contentColor = if (selectedSection.value == text) colorScheme.onSecondaryContainer else colorScheme.onPrimaryContainer,
                        containerColor = if (selectedSection.value == text) colorScheme.secondaryContainer else colorScheme.primaryContainer
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
                .weight(0.15f)
                .background(colorScheme.surface)
        ) {
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Row(horizontalArrangement = Arrangement.Center) {
                    /*Text(
                        "Mano de obra: %.2f €   \nMateriales: %.2f €   \nTotal: %.2f €".format(
                            precioManoDeObra,
                            precioTodosMateriales,
                            precioManoDeObra + viewModelMaterial.sumarPrecioMateriales()
                        ),
                        color = colorScheme.onBackground
                    )*/
                    Text("Total: ${precioManoDeObra + precioMateriales}",
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
                    onClick = {
                        val nota = Nota(
                            id = UUID.randomUUID().toString(),
                            personaContacto = personaContacto,
                            clienteId = clienteId,
                            trabajadorId = SessionManager.getWorkerId(context),
                            trabajoRealizado = trabajoRealizado,
                            notaCerradaEn = LocalDateTime.now().toString(),
                            fecha = LocalDate.now().toString(),
                            observacionesPublias = observacionesPublicas,
                            observacionesPrivadas = observacionesPrivadas,
                            firma = "firmado"
                        )
                        if (nota.clienteId.trim().isBlank()) {
                            showToast(context, "Cliente no seleccionado, no se ha creado la nota")
                        } else if (notaViewModel.comprobarNota(notaViewModel.state.notas, nota) == 1) {
                            showToast(context, "Has insertado la misma nota 2 veces(trabajo y fecha identicos)")
                        } else if (notaViewModel.comprobarNota(notaViewModel.state.notas, nota) == 0) {
                            notaViewModel.createNota(nota)
                            showToast(context, "Nota insertada con éxito")
                        }
                    },
                ) {
                    Text(
                        text = "Guardar Nota",
                        color = colorScheme.background,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}