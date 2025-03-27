package com.example.psnotes.data.state

import com.example.psnotes.data.model.Nota

data class NotaState(
    val notas: List<Nota> = emptyList(),
    var nombreCliente: String = "",
    var personaContacto: String = "",
    var clienteId: String? = "",
    val trabajadorId: String? = "",
    var trabajoRealizado: String? = "",
    val notaCerradaEn: String? = "",
    val fecha: String? = "",
    val observacionesPublicas: String? = "",
    val observacionesPrivadas: String? = "",
    val firmaUri: String? = null,
    val idNota: String? = ""

)