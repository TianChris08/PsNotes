package com.example.psnotes.data.model

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Nota(
    @PrimaryKey(autoGenerate = true)
    private val id: Int, //aosiuhd9283h49uhdf
    val personaContacto: String, // Pepe el cortados
    val cliente: Cliente?, // Bar la sardina
    //val lineasNotas: LineasNotas?,
    val trabajador: Trabajador, // Pol
    val notaCerradaEn: String?, // hoy a las 15:32:55 // Convertir a fecha luego
    val fecha: String?, // Convertir a fecha luego
    val observacionesPublias: String?,
    val observacionesPrivadas: String?,
    val firma: Image?
)