package com.example.psnotes.ui.viewmodel

import com.example.psnotes.data.repository.ClienteDAO
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import kotlin.test.Test

class ClienteViewModelTest {

    private lateinit var viewModel: ClienteViewModel
    private val mockDao: ClienteDAO = mock()  // Usamos Mockito para simular el DAO

    @BeforeEach
    fun setup() {
        viewModel = ClienteViewModel(mockDao)  // Inicializamos el ViewModel con el DAO simulado
    }

    @Test
    fun `test changeFiscalName updates state correctly`() {
        val newFiscalName = "Nuevo Nombre Fiscal"

        viewModel.changeFiscalName(newFiscalName)

        assertEquals(newFiscalName, viewModel.state.nombreFiscalCliente)  // Verifica que el nombre fiscal se actualizó correctamente
    }

    @Test
    fun `test createClient calls insertClient on valid data`() {
        // Datos válidos
        val nombreFiscal = "Nombre Fiscal"
        val nombreComercial = "Nombre Comercial"
        val telefono = "+123456789"
        val correo = "correo@dominio.com"
        val coordenadas = "40.7128,-74.0060"  // Opcional

        // Llamada a la función
        val result = viewModel.createClient(nombreFiscal, nombreComercial, telefono, correo, coordenadas)

        // Verifica que los errores de validación sean nulos (es decir, no hay errores)
        assertNull(result)

        // Verifica que el DAO haya sido llamado para insertar el cliente
        verify(mockDao).insertClient(any())  // Aquí puedes hacer una verificación más específica si es necesario
    }

    @Test
    fun `test createClient with invalid email returns validation error`() {
        // Datos con un correo inválido
        val nombreFiscal = "Nombre Fiscal"
        val nombreComercial = "Nombre Comercial"
        val telefono = "+123456789"
        val correo = "correo_invalido"
        val coordenadas = "40.7128,-74.0060"

        // Llamada a la función
        val result = viewModel.createClient(nombreFiscal, nombreComercial, telefono, correo, coordenadas)

        // Verifica que el error de validación de correo sea el esperado
        assertNotNull(result)
        assertTrue(result!!.containsKey("correoValid"))
        assertEquals("Correo electrónico inválido", result["correoValid"])
    }

    @org.junit.jupiter.api.Test
    fun getErroresValidacion() {

    }

    @org.junit.jupiter.api.Test
    fun getState() {

    }

    @org.junit.jupiter.api.Test
    fun getErrorGeneral() {

    }

    @org.junit.jupiter.api.Test
    fun changeFiscalName() {

    }

    @org.junit.jupiter.api.Test
    fun changeCommercialName() {

    }

    @org.junit.jupiter.api.Test
    fun deleteCliente() {

    }

    @org.junit.jupiter.api.Test
    fun createClientWithoutStore() {
    }

    @org.junit.jupiter.api.Test
    fun testCreateClientWithoutStore() {
    }

    @org.junit.jupiter.api.Test
    fun createClient() {
    }

    @org.junit.jupiter.api.Test
    fun testCreateClient() {
    }

}