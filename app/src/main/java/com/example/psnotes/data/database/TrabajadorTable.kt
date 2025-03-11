package com.example.psnotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.psnotes.data.model.Trabajador
import com.example.psnotes.data.repository.TrabajadorDAO


@Database(entities = [Trabajador::class], version = 1)
abstract class TrabajadorTable : RoomDatabase() {
    abstract val trabajadorDAO : TrabajadorDAO
}

