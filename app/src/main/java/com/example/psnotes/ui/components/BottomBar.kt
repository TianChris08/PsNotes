package com.example.psnotes.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Note
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BottomBar(navController: NavController, currentRoute: String) {

    val items = listOf(
        "Inicio" to Icons.Outlined.Home,
        "Buscar" to Icons.Outlined.Search,
        "Notas" to Icons.Outlined.Note,
        "Perfil" to Icons.Outlined.Face,
        "Ajustes" to Icons.Outlined.Settings
    )

    var selectedItem by remember { mutableStateOf(currentRoute) }


    BottomAppBar(
        containerColor = colorScheme.tertiary,
    ) {
        Row {
            items.forEach { (title, icon) ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = title,
                            tint = if (selectedItem == title) Color.DarkGray else colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            title,
                            color = if (selectedItem == title) Color.DarkGray else colorScheme.onBackground
                        )
                    },
                    selected = selectedItem == title,
                    onClick = {
                        selectedItem = title
                        navController.navigate(title)
                    }
                )
            }
        }
    }
}
