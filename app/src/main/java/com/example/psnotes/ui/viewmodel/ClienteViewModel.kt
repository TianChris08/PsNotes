package com.example.psnotes.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.repository.ClienteDAO
import com.example.psnotes.ui.screens.ClienteState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID
import java.util.regex.Pattern

class ClienteViewModel(
    private val dao: ClienteDAO
) : ViewModel() {

    var state by mutableStateOf(ClienteState())
        private set

    var mensajeError by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            try {
                dao.getClients().collectLatest { cliente ->
                    state = state.copy(clientes = cliente)
                }
            } catch (e: Exception) {
                mensajeError = "Error al cargar clientes: ${e.message}"
            }
        }
    }

    fun changeFiscalName(fiscalName:String) {
        state = state.copy(nombreFiscalCliente = fiscalName)

    }

    fun changeCommercialName(commercialName:String) {
        state = state.copy(nombreComercialCliente = commercialName)
    }

    fun deleteCliente(cliente: Cliente) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dao.deleteClient(cliente)
            } catch (e: Exception) {
                mensajeError = "Error al eliminar clientes: ${e.message}"
            }
        }
    }

    fun createClient() {
        createClient(
            state.nombreFiscalCliente,
            state.nombreComercialCliente,
            state.telefonoCliente,
            state.correoCliente
        )
        /*val cliente = Cliente(
            UUID.randomUUID().toString(),
            state.clienteFiscalName,
            state.clienteCommercialName,
            state.clienteTelefono,
            state.clienteCorreo
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertClient(cliente)
        }*/
    }

    fun createClient(nombreFiscal: String, nombreComercial: String, telefono: String, correo: String) {
        if (!validarDatos(nombreFiscal, nombreComercial, telefono, correo)) return

        val cliente = Cliente(
            UUID.randomUUID().toString(),
            nombreFiscal,
            nombreComercial,
            telefono,
            correo
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                dao.insertClient(cliente)
            } catch (e: Exception) {
                mensajeError = "Error al crear cliente: ${e.message}"
            }
        }
    }

    private fun validarDatos(nombreFiscal: String, nombreComercial: String, telefono: String, correo: String): Boolean {
        return when {
            nombreFiscal.isBlank() -> {
                mensajeError = "El nombre fiscal no puede estar vacío"
                false
            }
            nombreComercial.isBlank() -> {
                mensajeError = "El nombre comercial no puede estar vacío"
                false
            }
            !validarTelefono(telefono) -> {
                mensajeError = "Número de teléfono inválido"
                false
            }
            !validarCorreo(correo) -> {
                mensajeError = "Correo electrónico inválido"
                false
            }
            else -> {
                mensajeError = null // No hay errores
                true
            }
        }
    }

    private fun validarTelefono(telefono: String): Boolean {
        val regexTelefono = "^\\+?\\d{9,15}$"
        return Pattern.matches(regexTelefono, telefono)
    }

    private fun validarCorreo(correo: String): Boolean {
        val regexCorreo = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$"
        return Pattern.matches(regexCorreo, correo)
    }

}