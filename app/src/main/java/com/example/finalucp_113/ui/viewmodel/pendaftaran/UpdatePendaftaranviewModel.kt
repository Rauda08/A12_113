package com.example.finalucp_113.ui.viewmodel.pendaftaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.repository.PendaftaranRepository
import kotlinx.coroutines.launch

class UpdatePendaftaranViewModel(
    savedStateHandle: SavedStateHandle,
    private val pendaftaranRepository: PendaftaranRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertPendaftaranUIState())
        private set

    val id_pendaftaran: String = checkNotNull(savedStateHandle[DestinasiUpdatePendaftaran.id_pendaftaran])

    init {
        viewModelScope.launch {
            uiState = pendaftaranRepository.getPendaftaranByidPendaftaran(id_pendaftaran).toUiStatePendaftaran()
        }
    }

    fun updateInsertPendaftaranState(insertUiEvent: InsertPendaftaranEvent) {
        uiState = InsertPendaftaranUIState(pendaftaranEvent = insertUiEvent)
    }

    suspend fun UpdatePendaftaran(){
        viewModelScope.launch {
            try {
                pendaftaranRepository.updatePendaftaran(id_pendaftaran, uiState.pendaftaranEvent.toPendaftaran())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}