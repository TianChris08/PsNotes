package com.example.psnotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.psnotes.data.model.Nota
import com.example.psnotes.data.repository.NotaDAO

@Database(entities = [Nota::class], version = 1)
abstract class NotaTable : RoomDatabase() {
    abstract val notaDAO : NotaDAO
}