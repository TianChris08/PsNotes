package com.example.psnotes.ui.screens

import com.example.psnotes.data.model.Cliente

data class HomeState(
    val clientes: List<Cliente> = emptyList(),
    val clienteFiscalName: String = "",
    val clienteCommercialName: String = "",
    val clienteTelefono: String = "",
    val clienteCorreo: String = "",
    val clienteId: String? = null

)