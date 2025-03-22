package com.example.psnotes.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.psnotes.ui.viewmodel.NotaViewModel

@Composable
fun NotasScreen(paddingValues: PaddingValues, navController: NavController, notasViewModel: NotaViewModel?) {

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {

        val notas = notasViewModel!!.state.notas.size

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(notas) { nota ->
                Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = "Persona de contacto: ${notasViewModel.state.notas[nota].personaContacto}")
                        Text(text = "Fecha: ${notasViewModel.state.notas[nota].fecha ?: "Fecha no disponible"}")
                        Text(text = "Observaciones públicas: ${notasViewModel.state.notas[nota].observacionesPublias ?: "Sin observaciones"}")
                        Text(text = "Observaciones privadas: ${notasViewModel.state.notas[nota].observacionesPrivadas ?: "Sin observaciones"}")
                    }
                }
            }
        }
    }

}