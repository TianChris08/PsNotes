    package com.example.psnotes.ui.components

    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.layout.statusBarsPadding
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material.BottomNavigation
    import androidx.compose.material.BottomNavigationItem
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Home
    import androidx.compose.material.icons.filled.Person
    import androidx.compose.material.icons.outlined.Face
    import androidx.compose.material.icons.outlined.Home
    import androidx.compose.material.icons.outlined.Note
    import androidx.compose.material.icons.outlined.Search
    import androidx.compose.material.icons.outlined.Settings
    import androidx.compose.material3.BottomAppBar
    import androidx.compose.material3.Icon
    import androidx.compose.material3.MaterialTheme.colorScheme
    import androidx.compose.material3.NavigationBarItem
    import androidx.compose.material3.NavigationBarItemDefaults
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.text.style.TextDecoration
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.navigation.NavController


    /*@Composable
    fun BottomBar(navController: NavController) {

        //var selectedItem by remember { mutableStateOf(currentRoute) }

        BottomNavigation {
            BottomNavigationItem(
                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                label = { Text("Inicio") },
                selected = false,
                onClick = { navController.navigate("Inicio") }
            )
            BottomNavigationItem(
                icon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                label = { Text("Buscar") },
                selected = false,
                onClick = { navController.navigate("Buscar") }
            )
            BottomNavigationItem(
                icon = { Icon(Icons.Outlined.Note, contentDescription = null) },
                label = { Text("Notas") },
                selected = false,
                onClick = { navController.navigate("Notas") }
            )
            BottomNavigationItem(
                icon = { Icon(Icons.Outlined.Face, contentDescription = null) },
                label = { Text("Perfil") },
                selected = false,
                onClick = { navController.navigate("Perfil") }
            )
            BottomNavigationItem(
                icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                label = { Text("Ajustes") },
                selected = false,
                onClick = { navController.navigate("Ajustes") }
            )
        }
    }*/


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
            containerColor = colorScheme.primaryContainer,
            contentColor = colorScheme.onPrimaryContainer
        ) {
            Row {
                items.forEach { (title, icon) ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = title,
                                tint = if (selectedItem == title) colorScheme.onSecondaryContainer else colorScheme.onPrimaryContainer
                            )
                        },
                        selected = selectedItem == title,
                        onClick = {
                            selectedItem = title
                            navController.navigate(title)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = if (selectedItem == title) colorScheme.secondaryContainer else Color.Transparent
                        ),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                }
            }
        }
    }
