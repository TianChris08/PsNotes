package com.example.psnotes.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Router
import androidx.compose.material.icons.outlined.TextFormat
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.psnotes.data.SessionManager
import com.example.psnotes.data.model.Nota
import com.example.psnotes.ui.components.BotonLateral
import com.example.psnotes.ui.components.EmergenteClientes
import com.example.psnotes.ui.components.toastAutoCancelable
import com.example.psnotes.ui.screens.inicio.FirmaScreen
import com.example.psnotes.ui.screens.inicio.MaterialesScreen
import com.example.psnotes.ui.screens.inicio.Observaciones1Screen
import com.example.psnotes.ui.screens.inicio.Observaciones2Screen
import com.example.psnotes.ui.screens.inicio.TrabajoScreen
import com.example.psnotes.ui.viewmodel.ClienteViewModel
import com.example.psnotes.ui.viewmodel.MaterialViewModel
import com.example.psnotes.ui.viewmodel.NotaViewModel
import com.example.psnotes.ui.viewmodel.ObservacionesViewModel
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

    var clienteId by remember { mutableStateOf<String?>("") }
    var personaContacto by remember { mutableStateOf("") }

    val precioMateriales = viewModelMaterial.sumarPrecioMateriales()
    val precioManoDeObra by trabajoViewModel.precioManoDeObra.collectAsState()

    val selectedSection = remember { mutableStateOf("trabajo") }

    val observacionesPublicas by observacionesViewModel.observacionesPublicas.collectAsState()
    val observacionesPrivadas by observacionesViewModel.observacionesPrivadas.collectAsState()
    val trabajoRealizado by trabajoViewModel.trabajoRealizado



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (!Places.isInitialized()) {
            Places.initialize(context, "AIzaSyAmqr5VECPpGR-vlAo3mGqn60Dg24I9pV8")
        }

        // 🔹 Parte Superior - Información Cliente
        Row(
            modifier = Modifier
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
                    Button(
                        onClick = { clienteViewModel.mostrarEmergenteClientes.value = true }
                    ) { Text(clienteViewModel.clienteSeleccionado.value?.commercialName ?: "Selecciona un cliente") }


                    //Obtengo el id del cliente seleccionado
                    /*clienteId = miDesplegable(
                        modifier = Modifier.padding(top = 5.dp),
                        clienteViewModel = clienteViewModel
                    )*/

                    /*IconButton(
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
                    }*/
                }
                Row(
                    modifier = Modifier
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

        if (clienteViewModel.mostrarEmergenteClientes.value) {
            clienteId = EmergenteClientes(
                onDismiss = {
                    clienteViewModel.mostrarEmergenteClientes.value = false
                },
                clienteViewModel = clienteViewModel
            )
        }



        val colorFondo = colorScheme.tertiary.copy(alpha = 0.7f)

        // 🔹 Parte Media - Contenido Dinámico
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.60f)
                .background(
                    color = colorFondo,
                    shape = RectangleShape)
        ) {
            // 🔹 Botones de Cambio
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.2f),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ) {
                listOf(
                    Icons.Outlined.WorkOutline to "trabajo",
                    Icons.Outlined.Router to "materiales",
                    Icons.Outlined.NoteAlt to "observaciones1",
                    Icons.Outlined.NoteAlt to "observaciones2",
                    Icons.Outlined.TextFormat to "firma"
                ).forEach { (icon, text) ->
                    BotonLateral(Modifier.weight(1f), selectedSection, icon, text)
                }
            }
            //Seccion dinamica
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.8f)
                    .padding(12.dp)
            ) {
                when (selectedSection.value) {
                    "trabajo" -> TrabajoScreen(trabajoViewModel)
                    "materiales" -> MaterialesScreen(viewModelMaterial)
                    "observaciones1" -> Observaciones1Screen()
                    "observaciones2" -> Observaciones2Screen()
                    "firma" -> FirmaScreen()
                }
            }
        }


        // 🔹 Parte Baja - Información final y botón crear nota
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.15f)
                .background(colorScheme.surface)
        ) {
            Row (
                modifier = Modifier.fillMaxSize().padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(horizontalArrangement = Arrangement.Center) {
                    /*Text(
                        "Mano de obra: %.2f €   \nMateriales: %.2f €   \nTotal: %.2f €".format(
                            precioManoDeObra,
                            precioTodosMateriales,
                            precioManoDeObra + viewModelMaterial.sumarPrecioMateriales()
                        ),
                        color = colorScheme.onBackground
                    )*/
                    Text(
                        "Total: ${precioManoDeObra + precioMateriales}",
                        color = colorScheme.onBackground
                    )
                }
                Button(
                    colors = ButtonColors(
                        containerColor = colorScheme.tertiary,
                        contentColor = Color.Black,
                        disabledContainerColor = colorScheme.surfaceVariant,
                        disabledContentColor = colorScheme.onSurfaceVariant
                    ),
                    onClick = {
                        val nota = Nota(
                            id = UUID.randomUUID().toString(),
                            personaContacto = personaContacto,
                            clienteId = clienteId.toString(),
                            trabajadorId = SessionManager.getWorkerId(context),
                            trabajoRealizado = trabajoRealizado,
                            notaCerradaEn = LocalDateTime.now().toString(),
                            fecha = LocalDate.now().toString(),
                            observacionesPublias = observacionesPublicas,
                            observacionesPrivadas = observacionesPrivadas,
                            firma = "firmado"
                        )
                        if (nota.clienteId.trim().isBlank()) {
                            toastAutoCancelable(context, "Cliente no seleccionado, no se ha creado la nota")
                        } else if (notaViewModel.comprobarNota(
                                notaViewModel.notasState,
                                nota
                            ) == 1
                        ) {
                            toastAutoCancelable(
                                context,
                                "Has insertado la misma nota 2 veces(trabajo y fecha identicos)"
                            )
                        } else if (notaViewModel.comprobarNota(
                                notaViewModel.notasState,
                                nota
                            ) == 0
                        ) {
                            notaViewModel.createNota(nota)
                            toastAutoCancelable(context, "Nota insertada con éxito")
                        }
                    },
                ) {
                    Text(
                        text = "Guardar Nota",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}