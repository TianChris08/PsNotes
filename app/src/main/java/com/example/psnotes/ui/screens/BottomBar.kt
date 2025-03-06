package com.example.psnotes.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.psnotes.DefaultScreen

@Composable
fun BottomBar(navController: NavController) {
    val itemsNames = listOf("Inicio", "Buscar", "Favoritos", "Perfil", "Ajustes")
    BottomAppBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = itemsNames[0]) },
            label = { Text(itemsNames[0]) },
            selected = false, // Aquí puedes gestionar el estado
            onClick = { navController.navigate(itemsNames[0]) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = itemsNames[1]) },
            label = { Text(itemsNames[1]) },
            selected = false, // Aquí puedes gestionar el estado
            onClick = { /* Navegar según el screen */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = itemsNames[2]) },
            label = { Text(itemsNames[2]) },
            selected = false, // Aquí puedes gestionar el estado
            onClick = { /* Navegar según el screen */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Face, contentDescription = itemsNames[3]) },
            label = { Text(itemsNames[3]) },
            selected = false, // Aquí puedes gestionar el estado
            onClick = { /* Navegar según el screen */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = itemsNames[4]) },
            label = { Text(itemsNames[4]) },
            selected = false, // Aquí puedes gestionar el estado
            onClick = { /* Navegar según el screen */ }
        )
    }
}
