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
    modifier: Modifier,
    selectedSection: MutableState<String>,
    icon: ImageVector,
    text: String
) {

    val scaleTrabajos by animateIntAsState(if (selectedSection.value == "trabajo") 75 else 65)
    val scaleMateriales by animateIntAsState(if (selectedSection.value == "materiales") 75 else 65)
    val scaleFirma by animateIntAsState(if (selectedSection.value == "firma") 75 else 65)
    val scaleObservaciones1 by animateIntAsState(if (selectedSection.value == "observaciones1") 75 else 65)
    val scaleObservaciones2 by animateIntAsState(if (selectedSection.value == "observaciones2") 75 else 65)

    FloatingActionButton(
        modifier = modifier
            .fillMaxHeight()
            .border(
                width = 2.dp,
                color = colorScheme.background,
                shape = RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 8.dp
                )
            )
            .width(
                when (text) {
                    "trabajo" -> scaleTrabajos.dp
                    "materiales" -> scaleMateriales.dp
                    "firma" -> scaleFirma.dp
                    "observaciones1" -> scaleObservaciones1.dp
                    else -> scaleObservaciones2.dp
                }
            ),
        onClick = {
            selectedSection.value = text
        },
        shape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 0.dp,
            bottomEnd = 0.dp,
            bottomStart = 8.dp
        ),
        contentColor = if (selectedSection.value == text) colorScheme.onSecondaryContainer else colorScheme.onPrimaryContainer,
        containerColor = if (selectedSection.value == text) colorScheme.secondaryContainer else colorScheme.primaryContainer
    ) {
        Icon(icon, contentDescription = text)
    }
}