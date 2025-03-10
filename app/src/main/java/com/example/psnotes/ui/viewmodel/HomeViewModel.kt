package com.example.psnotes.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.repository.ClienteDAO
import com.example.psnotes.ui.screens.HomeState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID

class HomeViewModel(
    private val dao: ClienteDAO
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    init {
        viewModelScope.launch {
            dao.getClients().collectLatest {
                state = state.copy(
                    clientes = it
                )
            }
        }
    }

    fun changeFiscalName(fiscalName:String) {
        state = state.copy(
            clienteFiscalName = fiscalName
        )
    }

    fun changeCommercialName(commercialName:String) {
        state = state.copy(
            clienteCommercialName = commercialName
        )
    }

    fun deleteCliente(cliente: Cliente) {
        viewModelScope.launch {
            dao.deleteClient(cliente)
        }
    }

    fun createClient() {
        val cliente = Cliente(
            UUID.randomUUID().toString(),
            state.clienteFiscalName,
            state.clienteCommercialName
        )
        viewModelScope.launch {
            dao.insertClient(cliente)
        }
    }

}