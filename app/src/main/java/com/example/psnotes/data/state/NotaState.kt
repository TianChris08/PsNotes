package com.example.psnotes.data.state

import com.example.psnotes.data.model.Nota

data class NotaState(
    val notas: List<Nota> = emptyList(),
    val personaContacto: String = "",
    val clienteId: String? = null,
    //val lineasNotas: LineasNotas?,
    val trabajadorId: String? = null,
    val notaCerradaEn: String? = "",
    val fecha: String? = "",
    val observacionesPublicas: String? = "",
    val observacionesPrivadas: String? = "",
    val firmaUri: String? = null,
    val idNota: String? = null

)