package com.example.psnotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.psnotes.data.model.Cliente
import com.example.psnotes.data.model.Nota
import com.example.psnotes.data.model.Material
import com.example.psnotes.data.model.Trabajador
import com.example.psnotes.data.repository.ClienteDAO
import com.example.psnotes.data.repository.NotaDAO
import com.example.psnotes.data.repository.MaterialDAO
import com.example.psnotes.data.repository.TrabajadorDAO

@Database(
    entities = [Cliente::class, Nota::class, Material::class, Trabajador::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val clienteDao: ClienteDAO
    abstract val notaDAO: NotaDAO
    abstract val materialDao: MaterialDAO
    abstract val trabajadorDAO: TrabajadorDAO
}

object AppDatabaseSingleton {
    private var instance: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return instance ?: synchronized(this) {
            val tempInstance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "ps_notes_database"
            ).build()
            instance = tempInstance
            tempInstance
        }
    }
}