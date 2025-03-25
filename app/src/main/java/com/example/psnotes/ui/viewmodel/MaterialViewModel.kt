package com.example.psnotes.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psnotes.data.model.Material
import com.example.psnotes.data.repository.MaterialDAO
import com.example.psnotes.data.state.MaterialState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class MaterialViewModel(
    private val dao: MaterialDAO
) : ViewModel() {
    var state by mutableStateOf(MaterialState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dao.getMateriales().collectLatest {
                state = state.copy(
                    materiales = it
                )
            }
        }
    }

    fun createMaterial() {
        val material = Material(
            UUID.randomUUID().toString(),
            state.nombreMaterial,
            state.tipoMaterial,
            state.categoriaMaterial,
            state.precioUnitarioMaterial,
            state.especificacionesMaterial,
            state.fechaExpiracionMaterial.toString(),
            state.estadoMaterial
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dao.insertMaterial(material)
            } catch (e: Exception) {
                Log.e("MaterialViewModel", "Error al insertar material: ${e.localizedMessage}")
            }
        }
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

    fun searchMateriales(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Actualiza el estado con materiales filtrados
                val filteredMateriales = state.materiales.filter {
                    it.nombre.contains(query, ignoreCase = true) ||
                    it.tipo?.contains(query, ignoreCase = true) == true ||
                    it.especificaciones?.contains(query, ignoreCase = true) == true
                }

                // Actualiza el estado con los materiales filtrados
                state = state.copy(
                    materialesFiltrados = filteredMateriales
                )
            } catch (e: Exception) {
                Log.e("MaterialViewModel", "Error al buscar materiales: ${e.localizedMessage}")
            }
        }
    }

    fun addMaterialSeleccionado(nombre: String) {
        state = state.copy(
            materialesSeleccionados = state.materialesSeleccionados.toMutableMap().apply {
                this[nombre] = 0 // Añadir con cantidad 0
            }
        )
    }

    fun updateMaterialQuantity(nombre: String, quantity: Int) {
        state = state.copy(
            materialesSeleccionados = state.materialesSeleccionados.toMutableMap().apply {
                this[nombre] = quantity
            }
        )
    }

    fun removeMaterialSeleccionado(nombre: String) {
        state = state.copy(
            materialesSeleccionados = state.materialesSeleccionados.toMutableMap().apply {
                remove(nombre)
            }
        )
    }
}