package com.example.psnotes.ui.components

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiDesplegable(viewModel: ClienteViewModel) {
    val clientes = viewModel.clientes.value

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Selecciona un cliente") }
    val opciones = listOf("Opción 1", "Opción 2", "Opción 3")

    LaunchedEffect(Unit) {
        println("Ejecutando obtenerClientes()")
        viewModel.obtenerClientes()
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = it
        } // Permite desplegar desde cualquier parte del TextField
    ) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true, // Evita edición manual
            label = { Text("Selecciona una opción") },
            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Abrir menú",
                        Modifier.clickable { expanded = !expanded }
                    )
                }
            },
            modifier = Modifier.menuAnchor() // Asegura que el menú se alinee correctamente
        )

        // Menú desplegable con los clientes
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            clientes.forEach { cliente ->
                DropdownMenuItem(
                    text = { Text(cliente.nombreFiscal) },
                    onClick = {
                        selectedOption = cliente.nombreFiscal  // Establecer el nombre comercial seleccionado
                        expanded = false  // Cerrar el menú
                    }
                )
            }
        }
    }
}
*/