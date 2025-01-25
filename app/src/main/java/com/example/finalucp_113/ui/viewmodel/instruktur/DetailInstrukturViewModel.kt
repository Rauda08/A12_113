package com.example.finalucp_113.ui.viewmodel.instruktur

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.model.Instruktur
import com.example.finalucp_113.repository.InstrukturRepository
import com.example.finalucp_113.ui.view.instruktur.DestinasiDetailInstruktur
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailInstrukturUiState{
    data class Success(val instruktur: Instruktur) : DetailInstrukturUiState()
    object Error : DetailInstrukturUiState()
    object Loading : DetailInstrukturUiState()
}

class DetailInstrukturViewModel(
    savedStateHandle: SavedStateHandle,
    private val instrukturRepository: InstrukturRepository
) : ViewModel() {

    private val id_instruktur: String = checkNotNull(savedStateHandle[DestinasiDetailInstruktur.id_instruktur])
    var detailInstrukturUiState: DetailInstrukturUiState by mutableStateOf(DetailInstrukturUiState.Loading)
        private set

    init {
        getinstrukturbyId()
    }

    fun getinstrukturbyId(){
        viewModelScope.launch {
            detailInstrukturUiState = DetailInstrukturUiState.Loading
            detailInstrukturUiState = try {
                DetailInstrukturUiState.Success(instruktur = instrukturRepository.getInstrukturByidInstruktur(id_instruktur))
            } catch (e: IOException) {
                DetailInstrukturUiState.Error
            }
        }
    }
}
fun Instruktur.toDetailUiEvent () : InsertInstrukturEvent {
    return InsertInstrukturEvent(
        id_instruktur = id_instruktur,
        nama_instruktur = nama_instruktur,
        email = email,
        no_telepon = no_telepon,
        deskripsi = deskripsi
    )
}