package com.example.psnotes.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class NotaConDetalles(
    @Embedded val nota: Nota,
    @Relation(parentColumn = "clienteId", entityColumn = "id")
    val cliente: Cliente?,
    @Relation(parentColumn = "trabajadorId", entityColumn = "id")
    val trabajador: Trabajador?
)
