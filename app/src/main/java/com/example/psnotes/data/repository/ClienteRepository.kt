package com.example.psnotes.data.repository

/*class ClienteRepository(private val userDAO: ClienteDAO) {

    suspend fun getClients(): List<Cliente> {
        val entities = userDAO.getClients()
        return entities.map {
            Cliente(name = it.name)
        }
    }

    suspend fun insertClient(cliente: Cliente) {
        val entity = ClienteEntity(name = cliente.name)
        userDAO.insertClient(entity)
    }
}*/