package com.example.psnotes.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.psnotes.ui.viewmodel.NotaViewModel

@Composable
fun NotasScreen(paddingValues: PaddingValues, navController: NavController, notasViewModel: NotaViewModel) {

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
        ) {
            item {
                Card { Text(text= "Hola1") }
            }
            item {
                Card { Text(text= "Hola2") }
            }
        }
    }

}