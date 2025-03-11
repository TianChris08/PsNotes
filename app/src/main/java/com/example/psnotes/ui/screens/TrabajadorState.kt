package com.example.psnotes.ui.screens

import com.example.psnotes.data.model.Trabajador

data class TrabajadorState(
    val trabajadores: List<Trabajador> = emptyList(),
    val codigoTrabajador: Int = 0,
    val nombre: String = "",
    val tarifa: Double = 0.00,
    val pin: Int = 0,
    val idTrabjador : String? = null,
)