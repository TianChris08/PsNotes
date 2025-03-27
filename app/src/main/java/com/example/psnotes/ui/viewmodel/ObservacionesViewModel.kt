package com.example.psnotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ObservacionesViewModel : ViewModel() {

    private var _observacionesPublicas = MutableStateFlow("")
    val observacionesPublicas: StateFlow<String> = _observacionesPublicas.asStateFlow()

    private var _observacionesPrivadas = MutableStateFlow("")
    val observacionesPrivadas: StateFlow<String> = _observacionesPrivadas.asStateFlow()

    fun actualizarObservacionesPublicas(nuevaObservacion: String) {
        _observacionesPublicas.value = nuevaObservacion
    }

    fun actualizarObservacionesPrivadas(nuevaObservacion: String) {
        _observacionesPrivadas.value = nuevaObservacion
    }
}