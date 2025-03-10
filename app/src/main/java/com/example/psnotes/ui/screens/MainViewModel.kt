package com.example.psnotes.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.repository.ClienteDAO
//import com.example.psnotes.data.repository.ClienteRepository
import kotlinx.coroutines.launch

/*class MainViewModel(
    private val dao: ClienteDAO
) : ViewModel() {
    var state by mutableStateOf(MainState())
    private set


    init {
        viewModelScope.launch {
            state = state.copy(
                clientes = clienteRepository.getClients()
            )
        }
    }


    fun onNameChange(name: String) {
        state = state.copy(
            name = name
        )
    }

    fun saveCliente() {
        val cliente = Cliente(
            name = state.name
        )
        viewModelScope.launch {
            clienteRepository.insertClient(cliente)
        }
    }

}*/