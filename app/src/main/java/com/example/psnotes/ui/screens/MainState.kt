package com.example.psnotes.ui.screens

import com.example.psnotes.data.model.Cliente

data class MainState(
    val name : String = "",
    val clientes: List<Cliente> = emptyList()
)