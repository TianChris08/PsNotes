package com.example.psnotes.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.psnotes.data.model.Material
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaterial(material: Material)

    @Query("SELECT * FROM Material")
    fun getMateriales() : Flow<List<Material>>

    @Delete
    suspend fun deleteMaterial(material: Material)
}