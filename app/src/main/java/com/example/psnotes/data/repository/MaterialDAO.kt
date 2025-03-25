package com.example.psnotes.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.psnotes.data.model.Material
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface MaterialDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaterial(material: Material)

    @Query("SELECT * FROM Material")
    fun getMateriales() : Flow<List<Material>>

    @Query("SELECT precioUnitario FROM Material where nombre = :nombreMaterial")
    fun getPrecioMaterial(nombreMaterial: String) : Double

    @Delete
    suspend fun deleteMaterial(material: Material)
}