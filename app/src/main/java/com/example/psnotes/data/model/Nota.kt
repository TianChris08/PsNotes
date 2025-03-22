package com.example.psnotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Nota(
    @PrimaryKey(autoGenerate = false)
    val id: String, //aosiuhd9283h49uhdf
    val personaContacto: String, // Pepe el cortados
    val clienteId: String?, // Bar la sardina
    //val lineasNotas: LineasNotas?,
    val trabajadorId: String?, // Pol
    val notaCerradaEn: String?, // hoy a las 15:32:55 // Convertir a fecha luego
    val fecha: String?, // Convertir a fecha luego
    val observacionesPublias: String?,
    val observacionesPrivadas: String?,
    val firma: String?
)