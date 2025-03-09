package com.example.psnotes.data.repository

import com.example.psnotes.data.model.Cliente

class ClienteRepository(private val clienteDao: ClienteDao = DummyClienteDao) { //TEST DummyClienteDao

    // Función para insertar un cliente
    suspend fun insertarCliente(cliente: Cliente) {
        clienteDao.insertarCliente(cliente)
    }

    // Función para obtener un cliente por ID
    suspend fun obtenerClientePorId(id: Int): Cliente? {
        return clienteDao.obtenerClientePorId(id)
    }

    // Función para obtener todos los clientes
    suspend fun obtenerTodosClientes(): List<Cliente> {
        return clienteDao.obtenerTodosClientes()
    }

    // Función para eliminar un cliente por ID
    suspend fun eliminarCliente(id: Int) {
        clienteDao.eliminarCliente(id)
    }
}
