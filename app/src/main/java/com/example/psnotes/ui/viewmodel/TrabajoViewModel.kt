package com.example.psnotes.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrabajoViewModel : ViewModel() {

    var _tiempoTrabajado = MutableStateFlow(0)
    val tiempoTrabajado: StateFlow<Int> = _tiempoTrabajado.asStateFlow()

    var trabajoRealizado = mutableStateOf("")

    private val _precioEstimado = MutableStateFlow(0.0)
    val precioEstimado: StateFlow<Double> = _precioEstimado.asStateFlow()

    private val _tarifaPorHora = MutableStateFlow(10.0) // Tarifa por defecto (10€/h)
    val tarifaPorHora: StateFlow<Double> = _tarifaPorHora.asStateFlow()

    fun setTarifa(nuevaTarifa: Double) {
        viewModelScope.launch(Dispatchers.Main) {
            _tarifaPorHora.value = nuevaTarifa
            actualizarPrecio()
        }
    }

    fun incrementarTiempo() {
        viewModelScope.launch(Dispatchers.Main) {
            _tiempoTrabajado.update { it + 30 }
            actualizarPrecio()
        }
    }

    fun decrementarTiempo() {
        viewModelScope.launch(Dispatchers.Main) {
            _tiempoTrabajado.update { if (it > 0) it - 30 else 0 }
            actualizarPrecio()
        }
    }

    private fun actualizarPrecio() {
        viewModelScope.launch(Dispatchers.Main) {
            _precioEstimado.value = (_tiempoTrabajado.value / 60.0) * _tarifaPorHora.value
        }
    }

}
