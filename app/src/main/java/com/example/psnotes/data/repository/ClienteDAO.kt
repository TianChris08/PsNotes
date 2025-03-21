package com.example.psnotes.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.model.ClienteConNotas
import com.example.psnotes.data.model.Nota
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClient(cliente: Cliente)

    @Query("SELECT * FROM clientes")
    fun getTodosClientesFlow() : Flow<List<Cliente>>

    @Query("SELECT * FROM clientes")
    suspend fun getTodosClientes(): List<Cliente>

    @Delete
    suspend fun deleteClient(cliente: Cliente)

    @Transaction
    @Query("SELECT * FROM clientes WHERE clienteId = :idCliente")
    suspend fun getClienteConNotas(idCliente: String): ClienteConNotas
}