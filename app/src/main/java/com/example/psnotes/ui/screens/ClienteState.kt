package com.example.psnotes.ui.screens

import com.example.psnotes.data.model.Cliente

data class ClienteState(
    val clientes: List<Cliente> = emptyList(),
    val nombreFiscalCliente: String = "",
    val nombreComercialCliente: String = "",
    val telefonoCliente: String = "",
    val correoCliente: String = "",
    val idCliente: String? = null

)