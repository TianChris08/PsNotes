package com.example.psnotes.ui.screens

import com.example.psnotes.data.model.Estado
import com.example.psnotes.data.model.Material
import com.example.psnotes.data.model.TipoMaterial
import java.time.LocalDateTime


data class MaterialState(
    val materiales: List<Material> = emptyList(),
    val nombreMaterial: String = "",
    val tipoMaterial: TipoMaterial? = null,
    val categoriaMaterial: String = "",
    val cantidadMaterial: Int? = null,
    val precioUnitarioMaterial: Double? = null,
    val especificacionesMaterial: String = "",
    val fechaExpiracionMaterial: LocalDateTime? = null,
    val estadoMaterial: Estado? = null,
    val idMaterial : String? = null,

    )