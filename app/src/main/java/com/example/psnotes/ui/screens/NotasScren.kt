package com.example.psnotes.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NotasScreen(paddingValues: PaddingValues, navController: NavController) {

    Box(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(15.dp)) {

        }
    }

}