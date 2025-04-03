package com.example.psnotes.ui.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BotonLateral(
    modifier: Modifier = Modifier,
    selectedSection: MutableState<String>,
    icon: ImageVector,
    text: String
) {

    val isSelected = selectedSection.value == text

    val scale by animateIntAsState(if (isSelected) 70 else 65)

    FloatingActionButton(
        modifier = modifier
            .fillMaxHeight()
            .then(
                if (!isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = colorScheme.background,
                    )
                } else Modifier // Sin borde derecho cuando está seleccionado
            )
            .width(scale.dp),
        onClick = {
            selectedSection.value = text
        },
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = if (isSelected) 0.dp else 8.dp, // Sin borde derecho cuando está seleccionado
            bottomEnd = if (isSelected) 0.dp else 8.dp,
            bottomStart = 0.dp
        ),
        contentColor = if (isSelected) colorScheme.onSecondaryContainer else colorScheme.onPrimaryContainer,
        containerColor = if (isSelected) colorScheme.secondaryContainer else colorScheme.primaryContainer
    ) {
        Icon(icon, contentDescription = text)
    }
}
