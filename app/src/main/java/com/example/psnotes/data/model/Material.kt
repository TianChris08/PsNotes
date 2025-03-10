package com.example.psnotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Material(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val nombre: String,
    val tipo: TipoMaterial,
    val categoria: String,
    val marca: String?,
    val modeloVersion: String?,
    val cantidad: Int,
    val precioUnitario: Double,
    val proveedor: String?,
    val especificaciones: String,
    val fechaCompra: String,
    val fechaExpiracion: String,
    val estado: Estado
)
    //val notasTrabajo: ArrayList<NotasTrabajo>?

enum class TipoMaterial {
    HARDWARE, SOFTWARE
}

enum class Estado {
    ENUSO, ROTO
}
