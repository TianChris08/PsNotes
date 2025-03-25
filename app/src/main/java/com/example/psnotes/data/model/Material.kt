package com.example.psnotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Material(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val nombre: String,
    val tipo: String?,
    val categoria: String?,
    val precioUnitario: Double,
    val especificaciones: String?,
    val fechaExpiracion: String? = null,
    val estado: String?
)
    //val notasTrabajo: ArrayList<NotasTrabajo>?

enum class TipoMaterial {
    PORDECIDIR, HARDWARE, SOFTWARE
}

enum class Estado {
    ENUSO, ROTO, TALLER
}
