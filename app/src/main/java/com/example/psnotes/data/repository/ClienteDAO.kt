package com.example.psnotes.data.repository

//import com.example.psnotes.data.model.ClienteConNotas
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.psnotes.data.model.Cliente
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertClient(cliente: Cliente)

    @Query("SELECT * FROM Cliente")
    fun getTodosClientesFlow(): Flow<List<Cliente>>

    @Query("SELECT * FROM Cliente")
    fun getTodosClientes(): List<Cliente>

    @Delete
    fun deleteClient(cliente: Cliente)

    /*@Transaction
    @Query("SELECT * FROM Cliente WHERE id = :clienteId")
    fun obtenerClienteConNotas(clienteId: String): ClienteConNotas*/
}