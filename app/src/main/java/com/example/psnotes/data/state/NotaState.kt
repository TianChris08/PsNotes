package com.example.psnotes.data.state

import com.example.psnotes.data.model.Nota

data class NotaState(
    val notas: List<Nota> = emptyList(),
    val personaContacto: String = "",
    val clienteId: String? = "",
    //val lineasNotas: LineasNotas?,
    val trabajadorId: String? = "",
    val notaCerradaEn: String? = "",
    val fecha: String? = "",
    val observacionesPublicas: String? = "",
    val observacionesPrivadas: String? = "",
    val firmaUri: String? = "",
    val idNota: String? = ""
)