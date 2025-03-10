package com.example.psnotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.repository.ClienteDAO

@Database(entities = [Cliente::class], version = 1)
abstract class ClienteDatabase : RoomDatabase() {
    abstract val clienteDao : ClienteDAO
}