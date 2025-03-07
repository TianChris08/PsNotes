package com.example.psnotes.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.repository.ClienteRepository
import kotlinx.coroutines.launch

class ClienteViewModel(private val clienteRepository: ClienteRepository) : ViewModel() {

    private val _clientes = mutableStateOf<List<Cliente>>(emptyList())
    val clientes: State<List<Cliente>> get() = _clientes

    // Función para agregar un cliente
    fun agregarCliente(cliente: Cliente) {
        viewModelScope.launch {
            clienteRepository.insertarCliente(cliente)
        }
    }

    // Función para obtener todos los clientes
    fun obtenerClientes() {
        viewModelScope.launch {
            _clientes.value = clienteRepository.obtenerTodosClientes()
            // Aquí puedes manejar los resultados, por ejemplo, asignarlos a un LiveData
        }
    }
}