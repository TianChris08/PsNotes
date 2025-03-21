package com.example.psnotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "clientes")
data class Cliente(
    @PrimaryKey(autoGenerate = false)
    val clienteId : String,
    val fiscalName: String,
    val commercialName: String,
    val telefono: String,
    val correo: String,
    val coordenadasNegocio: String? = null
    //val notasTrabajo: ArrayList<NotasTrabajo>?

)