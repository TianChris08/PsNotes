package com.example.psnotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.repository.ClienteDao

@Database(entities = [Cliente::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clienteDao(): ClienteDao
}
