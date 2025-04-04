package com.example.psnotes.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psnotes.data.model.Material
import com.example.psnotes.data.repository.MaterialDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MaterialViewModel(
    private val dao: MaterialDAO
) : ViewModel() {
    var nuevaLineaForm by mutableStateOf(false)
    var listaMaterialesDesplegada by mutableStateOf(false)
    var nuevoMaterialdesplegado by mutableStateOf(false)


    var listaTodosMateriales by mutableStateOf(emptyList<Material>())
        private set

    var mapaMaterialesSeleccionados by mutableStateOf((emptyMap<String, Int>()))
        private set

    var precioMateriales by mutableDoubleStateOf(0.0)

    val precioUnitarioMaterial by mutableDoubleStateOf(0.0)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dao.getMateriales().collectLatest { materiales ->
                listaTodosMateriales = materiales
            }
        }
    }

    fun calcularPrecioSegunCantidad(nombre: String, cantidad: Int): Double {
        val material = listaTodosMateriales.find { it.nombre == nombre }
        return (material?.precioUnitario ?: 0.0) * cantidad
    }

    fun sumarPrecioMateriales(): Double {
        var total = 0.0
        mapaMaterialesSeleccionados.forEach { (nombre, cantidad) ->
            val material = listaTodosMateriales.find { it.nombre == nombre }
            if (material != null) {
                total += material.precioUnitario * cantidad
            }
        }
        precioMateriales = total
        return total
    }



    fun createMaterial(material: Material) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dao.insertMaterial(material)
            } catch (e: Exception) {
                Log.e("MaterialViewModel", "Error al insertar material: ${e.localizedMessage}")
            }
        }
    }

    fun addMaterialSeleccionado(nombre: String) {
        mapaMaterialesSeleccionados = mapaMaterialesSeleccionados.toMutableMap().apply {
            this[nombre] = 0 // Añadir con cantidad 0
        }
    }

    fun getPrecioMaterial(material: Material) : Double {
        return material.precioUnitario
    }
}