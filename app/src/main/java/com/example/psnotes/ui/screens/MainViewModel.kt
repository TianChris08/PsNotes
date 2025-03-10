package com.example.psnotes.ui.screens

/*import com.example.psnotes.data.repository.ClienteRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val clienteRepository : ClienteRepository
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