package com.example.psnotes.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrabajoViewModel : ViewModel() {

    private val _tiempoTrabajado = MutableStateFlow(0)
    val tiempoTrabajado: StateFlow<Int> = _tiempoTrabajado.asStateFlow()

    var trabajoRealizado = mutableStateOf("")

    private val _precioManoDeObra = MutableStateFlow(0.0)
    val precioManoDeObra: StateFlow<Double> = _precioManoDeObra.asStateFlow()

    private val _tarifaPorHora = MutableStateFlow(10.0) // Tarifa por defecto (10€/h)
    val tarifaPorHora: StateFlow<Double> = _tarifaPorHora.asStateFlow()

    fun actualizarTiempo(nuevoTiempo: Int) {
        _tiempoTrabajado.value = nuevoTiempo
    }

    fun incrementarTiempo() {
        viewModelScope.launch(Dispatchers.IO) {
            _tiempoTrabajado.update { it + 30 }
            withContext(Dispatchers.Main) {
                actualizarPrecio()
            }
        }
    }

    fun decrementarTiempo() {
        viewModelScope.launch(Dispatchers.IO) {
            _tiempoTrabajado.update { if (it > 0) it - 30 else 0 }
            withContext(Dispatchers.Main) {
                actualizarPrecio()
            }
        }
    }

    private fun actualizarPrecio() {
        _precioManoDeObra.value = (_tiempoTrabajado.value / 60.0) * _tarifaPorHora.value
    }

}
