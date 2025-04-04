package com.example.psnotes.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ObservacionesViewModel : ViewModel() {

    var observacionesPublicas by mutableStateOf("")
        private set

    var observacionesPrivadas by mutableStateOf("")
        private set

    fun actualizarObservacionesPublicas(nuevaObservacion: String) {
        observacionesPublicas = nuevaObservacion
    }

    fun actualizarObservacionesPrivadas(nuevaObservacion: String) {
        observacionesPrivadas = nuevaObservacion
    }
}