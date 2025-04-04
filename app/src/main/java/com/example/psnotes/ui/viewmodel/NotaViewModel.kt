package com.example.psnotes.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psnotes.data.model.Nota
import com.example.psnotes.data.repository.NotaDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class NotaViewModel(
    private val notaDao: NotaDAO
) : ViewModel() {

    var notasState by mutableStateOf(emptyList<Nota>())
        private set

    var errorGeneral by mutableStateOf<String?>(null)
        private set

    var personaContacto by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            try {
                notaDao.getNotasFlow().collectLatest { nota ->
                    notasState = nota
                }
            } catch (e: Exception) {
                errorGeneral = "Error al cargar notas: ${e.message}"
            }
        }
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

    fun onPersonaContactoChange(nuevoValor: String) {
        personaContacto = nuevoValor
    }
}