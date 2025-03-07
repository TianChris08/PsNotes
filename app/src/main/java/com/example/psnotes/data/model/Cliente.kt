package com.example.psnotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class Cliente(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nombreFiscal: String,
    val nombreComerial: String,
    val telefono: String,
    val correo: String,
    val notasTrabajo: ArrayList<NotasTrabajo>?
)