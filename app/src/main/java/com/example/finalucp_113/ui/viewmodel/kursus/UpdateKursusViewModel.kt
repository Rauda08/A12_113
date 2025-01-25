package com.example.finalucp_113.ui.viewmodel.kursus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.repository.KursusRepository
import kotlinx.coroutines.launch

class UpdateKursusViewModel(
    savedStateHandle: SavedStateHandle,
    private val kursusRepository: KursusRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertKursusUIState())
        private set

    val id_kursus: String = checkNotNull(savedStateHandle[DestinasiUpdateKursus.id_kursus])

    init {
        viewModelScope.launch {
            uiState = kursusRepository.getKursusByidKursus(id_kursus).toUiStateKursus()
        }
    }

    fun updateInsertKursusState(insertUiEvent: InsertKursusEvent) {
        uiState = InsertKursusUIState(kursusEvent = insertUiEvent)
    }

    suspend fun UpdateKursus(){
        viewModelScope.launch {
            try {
                kursusRepository.updateKursus(id_kursus, uiState.kursusEvent.toKursus())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}