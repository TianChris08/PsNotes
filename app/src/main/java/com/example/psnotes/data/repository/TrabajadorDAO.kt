package com.example.psnotes.data.repository

//import com.example.psnotes.data.model.TrabajadorConNotas
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.psnotes.data.model.Trabajador
import kotlinx.coroutines.flow.Flow

@Dao
interface TrabajadorDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrabajador(trabajador: Trabajador)

    @Query("SELECT * FROM Trabajador")
    fun getTrabajadores(): Flow<List<Trabajador>>

    @Delete
    suspend fun deleteTrabajador(trabajador: Trabajador)

    /*@Transaction
    @Query("SELECT * FROM Trabajador WHERE id = :trabajadorId")
    fun obtenerTrabajadorConNotas(trabajadorId: String): TrabajadorConNotas*/
}