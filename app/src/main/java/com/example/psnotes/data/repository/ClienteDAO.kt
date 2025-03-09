package com.example.psnotes.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.psnotes.data.model.Cliente

@Dao
interface ClienteDao {

    @Insert
    suspend fun insertarCliente(cliente: Cliente)

    @Query("SELECT * FROM clientes WHERE id = :id")
    suspend fun obtenerClientePorId(id: Int): Cliente?

    @Query("SELECT * FROM clientes")
    suspend fun obtenerTodosClientes(): List<Cliente>

    @Query("DELETE FROM clientes WHERE id = :id")
    suspend fun eliminarCliente(id: Int)
}


//TEST OBJECT
object DummyClienteDao : ClienteDao {
    override suspend fun insertarCliente(cliente: Cliente) { /* No-op */ }
    override suspend fun obtenerClientePorId(id: Int): Cliente? = null
    override suspend fun obtenerTodosClientes(): List<Cliente> = emptyList()
    override suspend fun eliminarCliente(id: Int) { /* No-op */ }
}