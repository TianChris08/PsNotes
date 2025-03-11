package com.example.psnotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Material(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val nombre: String,
    val tipo: TipoMaterial?,
    val categoria: String?,
    val cantidad: Int?,
    val precioUnitario: Double?,
    val especificaciones: String?,
    val fechaExpiracion: String? = null,
    val estado: Estado?
)
    //val notasTrabajo: ArrayList<NotasTrabajo>?

enum class TipoMaterial {
    PORDECIDIR, HARDWARE, SOFTWARE
}

enum class Estado {
    ENUSO, ROTO, TALLER
}
