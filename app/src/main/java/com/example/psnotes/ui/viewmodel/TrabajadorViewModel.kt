package com.example.psnotes.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psnotes.data.model.Trabajador
import com.example.psnotes.data.repository.TrabajadorDAO
import com.example.psnotes.ui.screens.TrabajadorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID

class TrabajadorViewModel(
    private val dao: TrabajadorDAO
) : ViewModel() {
    var state by mutableStateOf(TrabajadorState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dao.getTrabajadores().collectLatest {
                state = state.copy(
                    trabajadores = it
                )
            }
        }
    }

    fun changeCodigoTrabajador(codigo:Int) {
        state = state.copy(
            codigoTrabajador = codigo
        )
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
            //state.codigoTrabajador,
            state.nombre,
            state.tarifa,
            state.pin,
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertTrabajador(trabajador)
        }
    }

    fun createTrabajador(/*codigoTrabajador:Int,*/ nombre: String, tarifa: Double, pin: Int) {
        val trabajador = Trabajador(
            UUID.randomUUID().toString(),
            //codigoTrabajador,
            nombre,
            tarifa,
            pin
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertTrabajador(trabajador)
        }
    }
}