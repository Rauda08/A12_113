package com.example.finalucp_113.ui.viewmodel.instruktur

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.repository.InstrukturRepository
import kotlinx.coroutines.launch

class UpdateInstrukturViewModel(
    savedStateHandle: SavedStateHandle,
    private val instrukturRepository: InstrukturRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertInstrukturUIState())
        private set

    val id_instruktur: String = checkNotNull(savedStateHandle[DestinasiUpdateInstruktur.id_instruktur])

    init {
        viewModelScope.launch {
            uiState = instrukturRepository.getInstrukturByidInstruktur(id_instruktur).toUiStateInstruktur()
        }
    }

    fun updateInstrukturState(insertUiEvent: InsertInstrukturEvent) {
        uiState = InsertInstrukturUIState(instrukturEvent = insertUiEvent)
    }

    suspend fun UpdateInsertInstruktur(){
        viewModelScope.launch {
            try {
                instrukturRepository.updateInstruktur(id_instruktur, uiState.instrukturEvent.toInstruktur())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}