package com.example.psnotes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Canvas
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DrawBox(
    modifier: Modifier = Modifier,
    size: Int = 20 // Tamaño inicial del cuadro
) {
    var expanded by remember { mutableStateOf(false) } // Estado para expandir
    var pathList by remember { mutableStateOf(mutableListOf<List<Offset>>()) } // Lista de trayectorias de dibujo

    // Detectar el toque y agregar el nuevo punto
    fun insertNewPoint(offset: Offset) {
        pathList = pathList.toMutableList().apply {
            add(listOf(offset))
        }
    }

    fun updateLatestPath(offset: Offset) {
        pathList = pathList.mapIndexed { index, path ->
            if (index == pathList.lastIndex) path + offset
            else path
        } as MutableList<List<Offset>>
    }

    Column {
        Button(onClick = { expanded = !expanded }) {
            Text("Expandir")
        }

        Canvas(modifier = modifier
            .size(200.dp)
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { offset ->
                    insertNewPoint(offset)
                })
            }
            .pointerInput(Unit) {
                detectTapGestures(onPress = { offset ->
                    updateLatestPath(offset)
                })
            }) {
            // Dibujar las trayectorias
            pathList.forEach { path ->
                drawPath(
                    path = androidx.compose.ui.graphics.Path().apply {
                        path.forEachIndexed { index, offset ->
                            if (index == 0) moveTo(offset.x, offset.y)
                            else lineTo(offset.x, offset.y)
                        }
                    },
                    color = Color.White,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(
                        width = 5f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun DrawBoxPreview() {
    DrawBox(modifier = Modifier.fillMaxSize())
}
