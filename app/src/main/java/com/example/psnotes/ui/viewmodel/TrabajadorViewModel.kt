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
import com.example.psnotes.data.state.TrabajadorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID

class TrabajadorViewModel(
    private val dao: TrabajadorDAO
) : ViewModel() {
    var state by mutableStateOf(TrabajadorState())
        private set

    var recordarUsuario by mutableStateOf(false)

    init {
        viewModelScope.launch {
            dao.getTrabajadores().collectLatest {
                state = state.copy(
                    trabajadores = it
                )
            }
        }
    }

    fun changeNombre(nombre:String) {
        state = state.copy(
            nombre = nombre
        )
    }

    fun changeTarifa(tarifa:Double) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                tarifa = tarifa
            )
        }
    }

    fun changePin(pin:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                pin = pin
            )
        }
    }

    fun deleteTrabajador(trabajador: Trabajador) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteTrabajador(trabajador)
        }
    }

    fun createTrabajador() {
        val trabajador = Trabajador(
            UUID.randomUUID().toString(),
            state.nombre,
            state.tarifa,
            state.pin
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertTrabajador(trabajador)
        }
    }

    fun createTrabajador(nombre: String, tarifa: Double, pin: Int) {
        val trabajador = Trabajador(
            UUID.randomUUID().toString(),
            nombre,
            tarifa,
            pin
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertTrabajador(trabajador)
        }
    }

    fun clearSessionOnDestroy(context: Context) {
        if (!recordarUsuario) {
            SessionManager.clearSession(context)
        }
    }
}