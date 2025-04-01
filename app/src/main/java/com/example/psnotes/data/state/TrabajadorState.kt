package com.example.psnotes.data.state

import com.example.psnotes.data.model.Trabajador

data class TrabajadorState(
    val trabajadores: List<Trabajador> = emptyList(),
    val nombre: String = "",
    val pin: Int = 0,
    val tarifa: Double = 0.00
)