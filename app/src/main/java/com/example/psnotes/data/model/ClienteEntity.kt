package com.example.psnotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity//(tableName = "clientes")
data class ClienteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    /*val nombreComerial: String,
    val telefono: String,
    val correo: String,*/
    //val notasTrabajo: ArrayList<NotasTrabajo>?
)