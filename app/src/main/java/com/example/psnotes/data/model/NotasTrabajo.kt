package com.example.psnotes.data.model

import android.media.Image
import androidx.room.PrimaryKey
import java.time.LocalDateTime

data class NotasTrabajo(
    @PrimaryKey(autoGenerate = true)
    private val id: Int,
    val personaContacto: String,
    val cliente: ClienteEntity?,
    val lineasNotas: LineasNotas?,
    // val usuario: User,
    val notaCerradaEn: LocalDateTime?,
    val fecha: LocalDateTime?,
    val observacionesPublias: String?,
    val observacionesPrivadas: String?,
    val firma: Image?
)