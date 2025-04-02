package com.example.psnotes.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psnotes.data.model.Nota
import com.example.psnotes.data.repository.NotaDAO
import com.example.psnotes.data.state.NotaState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID


class NotaViewModel(
    private val notaDao: NotaDAO
) : ViewModel() {

    val erroresValidacion = mutableMapOf<String, String?>()

    var state by mutableStateOf(NotaState())
        private set

    var errorGeneral by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            try {
                notaDao.getNotasFlow().collectLatest { nota ->
                    state = state.copy(notas = nota)
                }
            } catch (e: Exception) {
                errorGeneral = "Error al cargar notas: ${e.message}"
            }
        }
    }


    fun changePersonaContacto(nuevaPersonaContacto: String) {
        state = state.copy(personaContacto = nuevaPersonaContacto)

    }

    fun changeFechaCierreNota(nuevaFechaCierre: String) {
        state = state.copy(notaCerradaEn = nuevaFechaCierre)
    }

    fun changeFechaAbrirNota(nuevaFechaAbertura: String) {
        state = state.copy(fecha = nuevaFechaAbertura)
    }

    fun changeObservacionesPublicas(nuevasObservacionesPublicas: String) {
        state = state.copy(observacionesPublicas = nuevasObservacionesPublicas)
    }

    fun changeObservacionesPrivadas(nuevasObservacionesPrivadas: String) {
        state = state.copy(observacionesPrivadas = nuevasObservacionesPrivadas)
    }


    fun deleteNota(nota: Nota) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                notaDao.deleteNota(nota)
            } catch (e: Exception) {
                errorGeneral = "Error al eliminar nota: ${e.message}"
            }
        }
    }

    fun createNota(
        personaContacto: String,
        clienteId: String,
        trabajadorId: String,
        trabajoRealizado: String?,
        notaCerradaEn: String?,
        fecha: String,
        observacionesPublias: String?,
        observacionesPrivadas: String?,
        firmaUri: String?
    ): MutableMap<String, String?>? {
        val nota = Nota(
            UUID.randomUUID().toString(),
            personaContacto,
            clienteId,
            trabajadorId.toString(),
            trabajoRealizado.toString(),
            notaCerradaEn,
            fecha.toString(),
            observacionesPublias,
            observacionesPrivadas,
            firmaUri
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                notaDao.insertNota(nota)
            } catch (e: Exception) {
                errorGeneral = "Error al crear nota: ${e.message}"
            }
        }

        return null
    }

    fun createNota(
        nota: Nota
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                notaDao.insertNota(nota)
            } catch (e: Exception) {
                errorGeneral = "Error al crear nota: ${e.message}"
            }
        }

    }


    fun createNota() {
        createNota(
            state.personaContacto,
            state.clienteId.toString(),
            state.trabajadorId.toString(),
            state.trabajoRealizado.toString(),
            state.notaCerradaEn,
            state.fecha.toString(),
            state.observacionesPublicas,
            state.observacionesPrivadas,
            state.firmaUri
        )
    }

    fun comprobarNota(notas: List<Nota>, notaNueva: Nota): Int {
        var resultado = 0
        for (nota in notas) {
            if (notaNueva.trabajoRealizado == nota.trabajoRealizado && notaNueva.fecha == nota.fecha) {
                resultado = 1
            } else {
                resultado = 0
            }
        }
        return resultado
    }
}