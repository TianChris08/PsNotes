package com.example.psnotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.psnotes.data.model.Material
import com.example.psnotes.data.repository.MaterialDAO

@Database(entities = [Material::class], version = 1)
abstract class MaterialTable : RoomDatabase() {
    abstract val materialDao : MaterialDAO
}