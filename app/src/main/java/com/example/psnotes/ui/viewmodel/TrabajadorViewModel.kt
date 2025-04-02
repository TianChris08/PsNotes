package com.example.psnotes.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psnotes.data.SessionManager
import com.example.psnotes.data.model.Trabajador
import com.example.psnotes.data.repository.TrabajadorDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID

class TrabajadorViewModel(
    private val dao: TrabajadorDAO
) : ViewModel() {
    var trabajadoresState by mutableStateOf(emptyList<Trabajador>())
        private set

    var recordarUsuario by mutableStateOf(false)

    init {
        viewModelScope.launch {
            dao.getTrabajadores().collectLatest {
                trabajadoresState = it
            }
        }
    }

    fun clearSessionOnDestroy(context: Context) {
        if (!recordarUsuario) {
            SessionManager.clearSession(context)
        }
    }
}