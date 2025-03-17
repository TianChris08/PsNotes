package com.example.psnotes.data.repository

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
    suspend fun insertClient(cliente: Cliente)

    @Query("SELECT * FROM Cliente")
    fun getClients() : Flow<List<Cliente>>

    @Query("SELECT * FROM Cliente")
    suspend fun getClientsOnce(): List<Cliente>

    @Delete
    suspend fun deleteClient(cliente: Cliente)
}