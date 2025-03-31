package com.example.psnotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Trabajador(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val nombre: String,
    val tarifa: Double,
    val pin: Int,
    //val notasCreadas: List<Nota>
)
