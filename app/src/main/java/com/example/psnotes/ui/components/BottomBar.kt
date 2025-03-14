package com.example.psnotes.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomBar(navController: NavController) {
    val itemsNames = listOf("Inicio", "Buscar", "Notas", "Perfil", "Ajustes")
    BottomAppBar {
        //Inicio
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = itemsNames[0]) },
            label = { Text(itemsNames[0]) },
            selected = false, // Aquí puedes gestionar el estado
            onClick = { navController.navigate(itemsNames[0]) }
        )
        //Buscar
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = itemsNames[1]) },
            label = { Text(itemsNames[1]) },
            selected = false, // Aquí puedes gestionar el estado
            onClick = { navController.navigate(itemsNames[1]) }
        )
        //Notas
        NavigationBarItem(
            icon = { Icon(Icons.Default.Note, contentDescription = itemsNames[2]) },
            label = { Text(itemsNames[2]) },
            selected = false, // Aquí puedes gestionar el estado
            onClick = { navController.navigate(itemsNames[2]) }
        )
        //Perfil
        NavigationBarItem(
            icon = { Icon(Icons.Default.Face, contentDescription = itemsNames[3]) },
            label = { Text(itemsNames[3]) },
            selected = false, // Aquí puedes gestionar el estado
            onClick = { navController.navigate(itemsNames[3]) }
        )
        //Ajustes
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = itemsNames[4]) },
            label = { Text(itemsNames[4]) },
            selected = false, // Aquí puedes gestionar el estado
            onClick = { navController.navigate(itemsNames[4]) }
        )
    }
}
