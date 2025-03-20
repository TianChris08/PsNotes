package com.example.psnotes.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.psnotes.data.model.Cliente
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNota(cliente: Cliente)

    @Query("SELECT * FROM Cliente")
    suspend fun getNotasFlow() : Flow<List<Cliente>>

    @Query("SELECT * FROM Cliente")
    suspend fun getNotas(): List<Cliente>

    @Delete
    suspend fun deleteNota(cliente: Cliente)

}