package com.example.psnotes.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.repository.ClienteDAO
import com.example.psnotes.ui.screens.HomeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID

class ClienteViewModel(
    private val dao: ClienteDAO
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dao.getClients().collectLatest {
                state = state.copy(
                    clientes = it
                )
            }
        }
    }

    fun changeFiscalName(fiscalName:String) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                clienteFiscalName = fiscalName
            )
        }
    }

    fun changeCommercialName(commercialName:String) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                clienteCommercialName = commercialName
            )
        }
    }

    fun deleteCliente(cliente: Cliente) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteClient(cliente)
        }
    }

    fun createClient() {
        val cliente = Cliente(
            UUID.randomUUID().toString(),
            state.clienteFiscalName,
            state.clienteCommercialName,
            state.clienteTelefono,
            state.clienteCorreo
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertClient(cliente)
        }
    }

    fun createClient(nombreFiscal: String, nombreComercial: String, telefono: String, correo: String) {
        val cliente = Cliente(
            UUID.randomUUID().toString(),
            nombreFiscal,
            nombreComercial,
            telefono,
            correo
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertClient(cliente)
        }
    }

}