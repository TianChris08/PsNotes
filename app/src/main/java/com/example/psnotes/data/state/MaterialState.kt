package com.example.psnotes.data.state

import com.example.psnotes.data.model.Material

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