package com.example.psnotes.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.repository.ClienteDAO
import com.example.psnotes.data.state.ClienteState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID
import java.util.regex.Pattern

class ClienteViewModel(
    private val dao: ClienteDAO
) : ViewModel() {

    val erroresValidacion = mutableMapOf<String, String?>()

    var state by mutableStateOf(ClienteState())
        private set

    var errorGeneral by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            try {
                dao.getClients().collectLatest { cliente ->
                    state = state.copy(clientes = cliente)
                }
            } catch (e: Exception) {
                errorGeneral = "Error al cargar clientes: ${e.message}"
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
                errorGeneral = "Error al eliminar clientes: ${e.message}"
            }
        }
    }

    fun createClientWithoutStore() {
        createClientWithoutStore(
            state.nombreFiscalCliente,
            state.nombreComercialCliente,
            state.telefonoCliente,
            state.correoCliente,
        )
    }

    fun createClient() {
        createClient(
            state.nombreFiscalCliente,
            state.nombreComercialCliente,
            state.telefonoCliente,
            state.correoCliente,
            state.coordenadasNegocio
        )
    }

    fun createClient(nombreFiscal: String, nombreComercial: String, telefono: String, correo: String, coordenadasNegocio: String?): MutableMap<String, String?>? {
        if (!validarDatos(nombreFiscal, nombreComercial, telefono, correo)) return erroresValidacion

        val cliente = Cliente(
            UUID.randomUUID().toString(),
            nombreFiscal,
            nombreComercial,
            telefono,
            correo,
            coordenadasNegocio
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                dao.insertClient(cliente)
            } catch (e: Exception) {
                errorGeneral = "Error al crear cliente: ${e.message}"
            }
        }

        return null
    }

    fun createClientWithoutStore(nombreFiscal: String, nombreComercial: String, telefono: String, correo: String): MutableMap<String, String?>? {
        if (!validarDatos(nombreFiscal, nombreComercial, telefono, correo)) return erroresValidacion

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
                errorGeneral = "Error al crear cliente: ${e.message}"
            }
        }

        return null
    }

    private fun validarDatos(nombreFiscal: String, nombreComercial: String, telefono: String, correo: String): Boolean {
        erroresValidacion.clear() // Limpiar errores previos

        return when {
            nombreFiscal.isBlank() -> {
                erroresValidacion["nombreFiscalBlank"] = "El nombre fiscal no puede estar vacío"
                false
            }
            nombreComercial.isBlank() -> {
                erroresValidacion["nombreComercialBlank"] = "El nombre comercial no puede estar vacío"
                false
            }
            telefono.isBlank() -> {
                erroresValidacion["telefonoBlank"] = "El telefono no puede estar vacío"
                false
            }
            correo.isBlank() -> {
                erroresValidacion["correoBlank"] = "El correo electrónico no puede estar vacío"
                false
            }
            !validarTelefono(telefono) -> {
                erroresValidacion["telefonoValid"] = "Número de teléfono inválido"
                false
            }
            !validarCorreo(correo) -> {
                erroresValidacion["correoValid"] = "Correo electrónico inválido"
                false
            }
            else -> true
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