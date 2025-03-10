package com.example.psnotes.deleteLater

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.psnotes.ui.screens.ClienteItem
import com.example.psnotes.ui.viewmodel.ClienteViewModel

/*CÓDICO DE REFERENCIA PARA TRABAJAR CON ROOM*/
@Composable
fun HomeScreen(viewModel: ClienteViewModel) {
    val state = viewModel.state

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Mis clientes", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        TextField(
            value = state.clienteFiscalName,
            onValueChange = { viewModel.changeFiscalName(it) },
            placeholder = { Text(text = "Nombre fiscal del cliente") }
        )
        TextField(
            value = state.clienteCommercialName,
            onValueChange = { viewModel.changeCommercialName(it) },
            placeholder = { Text(text = "Nombre comercial del cliente") }
        )
        Button(onClick = { viewModel.createClient() }) {
            Text(text = "Agregar Cliente")
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(state.clientes) {
                ClienteItem(
                    client = it,
                    modifier = Modifier.fillMaxWidth(),
                    onEdit = {
                        //viewModel.deleteCliente(it)
                    }, onDelete = {
                        viewModel.deleteCliente(it)
                    })
            }
        }
    }
}

