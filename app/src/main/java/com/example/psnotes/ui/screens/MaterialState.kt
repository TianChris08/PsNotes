package com.example.psnotes.ui.screens

import com.example.psnotes.data.model.Estado
import com.example.psnotes.data.model.Material
import com.example.psnotes.data.model.TipoMaterial
import java.time.LocalDateTime


data class MaterialState(
    val materiales: List<Material> = emptyList(),
    val nombreMaterial: String = "",
    val tipoMaterial: String = "",
    val categoriaMaterial: String = "",
    val cantidadMaterial: Int? = 0,
    val precioUnitarioMaterial: Double? = 0.0,
    val especificacionesMaterial: String = "",
    val fechaExpiracionMaterial: String? = "",
    val estadoMaterial: String? = "",
    val idMaterial : String? = "",
)