package com.example.psnotes.data.model

import android.media.Image
import androidx.room.PrimaryKey

data class NotaTrabajo(
    @PrimaryKey(autoGenerate = true)
    private val id: Int,
    val personaContacto: String,
    val cliente: Cliente?,
    val lineasNotas: LineasNotas?,
    val trabajador: Trabajador,
    val notaCerradaEn: String?, //Convertir a fecha luego
    val fecha: String?, //Convertir a fecha luego
    val observacionesPublias: String?,
    val observacionesPrivadas: String?,
    val firma: Image?
)