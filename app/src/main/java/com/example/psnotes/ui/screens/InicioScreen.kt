package com.example.psnotes.ui.screens


/*@Composable
fun InicioScreen(paddingValues: PaddingValues) {
    val mainViewModel: MainViewModel = viewModel()
    // Estado para controlar qué vista mostrar en la parte inferior
    val selectedSection = remember { mutableStateOf("trabajo") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // 🔹 Parte Superior - Información fija
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                .border(width = 1.dp, color = colorScheme.onBackground)
                .padding(horizontal = 10.dp)
        ) {
            //MiDesplegable(viewModel = mainViewModel)
            Text("Información Principal", style = MaterialTheme.typography.titleMedium)
            Text("Detalles sobre la pantalla...", style = MaterialTheme.typography.bodyMedium)
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
}*/