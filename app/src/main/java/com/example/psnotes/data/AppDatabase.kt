package com.example.psnotes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.model.Material
import com.example.psnotes.data.model.Nota
import com.example.psnotes.data.model.Trabajador
import com.example.psnotes.data.repository.ClienteDAO
import com.example.psnotes.data.repository.MaterialDAO
import com.example.psnotes.data.repository.NotaDAO
import com.example.psnotes.data.repository.TrabajadorDAO

@Database(entities = [Cliente::class, Nota::class, Trabajador::class, Material::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val clienteDao: ClienteDAO
    abstract val notaDao: NotaDAO
    abstract val trabajadorDao: TrabajadorDAO
    abstract val materialDao: MaterialDAO
}