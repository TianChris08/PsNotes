package com.example.psnotes.data.state

import com.example.psnotes.data.model.Material

data class MaterialState(
    val materiales: List<Material> = emptyList(),
    val materialesSeleccionados: MutableMap<String, Int> = mutableMapOf(), // Nuevo campo
    val materialesFiltrados: List<Material> = emptyList(),
    val nombreMaterial: String = "",
    val tipoMaterial: String = "",
    val categoriaMaterial: String = "",
    val precioUnitarioMaterial: Double = 0.0,
    val especificacionesMaterial: String = "",
    val fechaExpiracionMaterial: String? = "",
    val estadoMaterial: String? = "",
    val idMaterial : String? = "",
)