package com.example.psnotes.data.model

import androidx.room.PrimaryKey

data class LineasNotas(
    @PrimaryKey(autoGenerate = true)
    private val id: Int,
    val notaTrabajo: NotaTrabajo,
    //val productos: ArrayList<Producto>,
    //val servicios: Servicios,
    val idNota: Int,
    val idLinea: Int,
    val precio: Double,
    val descuento: Double,
    val importe: Double
)