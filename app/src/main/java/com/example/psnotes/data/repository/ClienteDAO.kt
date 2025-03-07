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