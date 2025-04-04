package com.example.psnotes.ui.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.repository.ClienteDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import java.util.regex.Pattern

class ClienteViewModel(
    private val dao: ClienteDAO
) : ViewModel() {

    val erroresValidacion = mutableMapOf<String, String?>()

    var clientesState by mutableStateOf(emptyList<Cliente>())
        private set

    var errorGeneral by mutableStateOf<String?>(null)
        private set

    private val _nombreCliente = mutableStateOf<String?>(null)
    val nombreCliente: String? get() = _nombreCliente.value

    private val _clienteSeleccionado by mutableStateOf<Cliente?>(null)
    val clienteSeleccionado: MutableState<Cliente?> = mutableStateOf(_clienteSeleccionado)
    val mostrarEmergenteClientes = mutableStateOf(false)



    init {
        viewModelScope.launch {
            try {
                dao.getTodosClientesFlow().collectLatest { nuevosClientes ->
                    // Verifica el contenido antes de sobrescribir
                    Log.d("NotaViewModel", "Clientes anteriores: $clientesState")
                    Log.d("NotaViewModel", "Nuevos clientes: $nuevosClientes")

                    // Aquí se sobrescribe la lista de clientes
                    clientesState = nuevosClientes

                    // Verifica el contenido después de sobrescribir
                    Log.d("NotaViewModel", "Clientes después de actualizar: $clientesState")
                }
            } catch (e: Exception) {
                errorGeneral = "Error al cargar clientes: ${e.message}"
            }
        }
    }

    fun getClientes() : List<Cliente> {
        return clientesState
    }

    fun buscarClientePorId(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (id != "nulo") {
                val cliente = dao.getClientePorId(id)
                withContext(Dispatchers.Main) {
                    _nombreCliente.value = cliente.commercialName
                }
            } else {
                errorGeneral = "No se ha encontrado el id del Cliente"
            }

        }
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