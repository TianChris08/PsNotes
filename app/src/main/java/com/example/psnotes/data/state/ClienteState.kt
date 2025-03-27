package com.example.psnotes.data.state

import com.example.psnotes.data.model.Cliente

data class ClienteState(
    val clientes: List<Cliente> = emptyList(),
    val nombreFiscalCliente: String = "",
    var nombreComercialCliente: String = "",
    val telefonoCliente: String = "",
    val correoCliente: String = "",
    val coordenadasNegocio: String? = "",
    val idCliente: String? = null

)