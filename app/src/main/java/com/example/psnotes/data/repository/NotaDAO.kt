package com.example.psnotes.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.psnotes.data.model.Nota
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNota(nota: Nota)

    @Query("SELECT * FROM Nota")
    fun getNotasFlow() : Flow<List<Nota>>

    @Query("SELECT * FROM Nota")
    suspend fun getNotas(): List<Nota>

    @Delete
    suspend fun deleteNota(nota: Nota)

}