package com.example.psnotes.ui.screens

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
/*import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import androidx.compose.ui.test.onNodeWithTag
import com.example.psnotes.data.SessionManager
import org.junit.Rule

class SplashScreenKtTest {

    // Inicializamos la regla para las pruebas de Compose
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: NavController
    private lateinit var context: Context

    @BeforeEach
    fun setUp() {
        // Mock de NavController y Context
        navController = mock(NavController::class.java)
        context = mock(Context::class.java)

        // Configuramos que la sesión esté logueada o no
        `when`(SessionManager.isLoggedIn(context)).thenReturn(true) // Cambia a 'false' si no quieres que esté logueado
    }

    @Test
    fun splashScreen_displaysLogo() {
        // Inicia el composable SplashScreen
        composeTestRule.setContent {
            SplashScreen(context = context, navController = navController)
        }

        // Verifica que el logo de Pensisoft sea visible
        composeTestRule.onNodeWithContentDescription("Logotipo de Pensisoft")
            .assertIsDisplayed()

        // Verifica que el texto "PsTask" esté visible
        composeTestRule.onNodeWithText("PsTask")
            .assertIsDisplayed()
    }

    @Test
    fun splashScreen_navigation_whenLoggedIn() {
        // Inicia el composable SplashScreen
        composeTestRule.setContent {
            SplashScreen(context = context, navController = navController)
        }

        // Verifica que la navegación ocurra a la pantalla de "Inicio" si está logueado
        composeTestRule.onNodeWithTag("SplashScreen")
            .performClick() // Simula la interacción (aunque normalmente se dispara automáticamente)

        // Verifica que se haya navegado a la pantalla "Inicio"
        `verify`(navController).navigate("Inicio")
    }

    @Test
    fun splashScreen_navigation_whenNotLoggedIn() {
        // Cambiar el mock para simular que el usuario no está logueado
        `when`(SessionManager.isLoggedIn(context)).thenReturn(false)

        // Inicia el composable SplashScreen
        composeTestRule.setContent {
            SplashScreen(context = context, navController = navController)
        }

        // Verifica que se haya navegado a la pantalla de "InicioSesion"
        `verify`(navController).navigate("InicioSesion")
    }
}*/