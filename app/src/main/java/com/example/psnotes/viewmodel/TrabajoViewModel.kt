package com.example.psnotes.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TrabajoViewModel : ViewModel() {
    var counter = mutableStateOf(0)
    var trabajoRealizado = mutableStateOf("")
}
