package com.example.psnotes.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psnotes.data.model.Estado
import com.example.psnotes.data.model.Material
import com.example.psnotes.data.model.TipoMaterial
import com.example.psnotes.data.repository.MaterialDAO
import com.example.psnotes.data.state.MaterialState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

class MaterialViewModel(
    private val dao: MaterialDAO
) : ViewModel() {
    var state by mutableStateOf(MaterialState())
        private set

    var _cantidadMaterial = MutableStateFlow(0)
    val cantidadMaterial: StateFlow<Int> = _cantidadMaterial.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dao.getMateriales().collectLatest {
                state = state.copy(
                    materiales = it
                )
            }
        }
    }

    fun changeNombreMaterial(nombreMaterial:String) {
        viewModelScope.launch(Dispatchers.Main) {
            state = state.copy(nombreMaterial = nombreMaterial )
        }
    }

    fun changeTipoMaterial(tipoMaterial: String) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                tipoMaterial = tipoMaterial
            )
        }
    }

    fun changeCategoriaMaterial(categoriaMaterial: String) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                categoriaMaterial = categoriaMaterial
            )
        }
    }

    fun changeCantidadMaterial(cantidadMaterial: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                cantidadMaterial = cantidadMaterial
            )
        }
    }

    fun changePrecioUnitarioMaterial(precioUnitario: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                precioUnitarioMaterial = precioUnitario
            )
        }
    }

    fun changeEspecificacionesMaterial(especificaciones: String) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                especificacionesMaterial = especificaciones
            )
        }
    }

    fun changeFechaExpiracionMaterial(fechaExpiracion: String) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                fechaExpiracionMaterial = fechaExpiracion
            )
        }
    }

    fun changeEstadoMaterial(estado: String) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                estadoMaterial = estado
            )
        }
    }

    fun deleteMaterial(material: Material) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteMaterial(material)
        }
    }

    fun incrementarCantidad() {
        viewModelScope.launch(Dispatchers.Main) {
            _cantidadMaterial.update { it + 1 }
        }
    }

    fun decrementarCantidad() {
        viewModelScope.launch(Dispatchers.Main) {
            _cantidadMaterial.update { if (it > 0) it - 1 else 0 }
        }
    }

    fun createMaterial() {
        val material = Material(
            UUID.randomUUID().toString(),
            state.nombreMaterial,
            state.tipoMaterial,
            state.categoriaMaterial,
            state.cantidadMaterial,
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

    fun createMaterial(
        nombre: String,
        tipo: String?,
        categoria: String,
        cantidad: Int,
        precioUnitario: Double,
        especificaciones: String,
        fechaExpiracion: String,
        estado: String
    ) {
        val material = Material(
            UUID.randomUUID().toString(),
            nombre,
            tipo,
            categoria,
            cantidad,
            precioUnitario,
            especificaciones,
            fechaExpiracion.toString(),
            estado
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertMaterial(material)
        }
    }
}