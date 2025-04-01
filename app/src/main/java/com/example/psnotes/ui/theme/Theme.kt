package com.example.psnotes.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Green40,
    secondary = Green60,
    tertiary = Green20,

    // Other default colors to override
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF1C1B1F),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = WhitePs,
    onSurface = Color(0xFFFFFBFE),

    // Contenedor SIN SELECCIONAR
    primaryContainer = Green40,
    onPrimaryContainer = Color.White,

    // Contenedor SELECCIONADO
    secondaryContainer = Green20,
    onSecondaryContainer = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = Purple60,
    tertiary = Purple20,

    // Other default colors to override
    background = Color(0xFFD9D1F3),
    surface = Color(0xFFD7CFEF),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = BlackPs,
    onSurface = Color(0xFF444343),

    // Contenedor SIN SELECCIONAR
    primaryContainer = Purple40,
    onPrimaryContainer = Color.White,

    // Contenedor SELECCIONADO
    secondaryContainer = Purple20,
    onSecondaryContainer = Color.Black
)

@Composable
fun PsNotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}