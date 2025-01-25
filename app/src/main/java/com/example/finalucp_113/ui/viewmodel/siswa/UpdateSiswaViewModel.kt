package com.example.finalucp_113.ui.viewmodel.siswa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.repository.SiswaRepository
import kotlinx.coroutines.launch

class UpdateSiswaViewModel(
    savedStateHandle: SavedStateHandle,
    private val siswaRepository: SiswaRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertSiswaUIState())
        private set

    val id_siswa: String = checkNotNull(savedStateHandle[DestinasiUpdateSiswa.id_siswa])

    init {
        viewModelScope.launch {
            uiState = siswaRepository.getSiswaByidSiswa(id_siswa).toUiStateSiswa()
        }
    }

    fun updateInsertMhsState(insertUiEvent: InsertSiswaEvent) {
        uiState = InsertSiswaUIState(siswaEvent = insertUiEvent)
    }

    suspend fun UpdateSiswa(){
        viewModelScope.launch {
            try {
                siswaRepository.updateSiswa(id_siswa, uiState.siswaEvent.toSiswa())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}