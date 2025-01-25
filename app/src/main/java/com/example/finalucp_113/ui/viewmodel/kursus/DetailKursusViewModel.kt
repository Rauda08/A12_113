package com.example.finalucp_113.ui.viewmodel.kursus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.model.Kursus
import com.example.finalucp_113.repository.KursusRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailkursusUiState{
    data class Success(val kursus: Kursus) : DetailkursusUiState()
    object Error : DetailkursusUiState()
    object Loading : DetailkursusUiState()
}

class DetailkursusViewModel(
    savedStateHandle: SavedStateHandle,
    private val kursusRepository: KursusRepository
) : ViewModel() {

    private val id_kursus: String = checkNotNull(savedStateHandle[DestinasiDetailKursus.id_kursus])
    var detailkursusUiState: DetailkursusUiState by mutableStateOf(DetailkursusUiState.Loading)
        private set

    init {
        getkursusbyId()
    }

    fun getkursusbyId(){
        viewModelScope.launch {
            detailkursusUiState = DetailkursusUiState.Loading
            detailkursusUiState = try {
                DetailkursusUiState.Success(kursus = kursusRepository.getKursusByidKursus(id_kursus))
            } catch (e: IOException) {
                DetailkursusUiState.Error
            }
        }
    }
}
fun Kursus.toDetailUiEvent () : InsertKursusEvent {
    return InsertKursusEvent(
        id_kursus = id_kursus,
        nama_kursus = nama_kursus,
        deskripsi = deskripsi,
        kategori = kategori,
        harga = harga,
        id_instruktur = id_instruktur
    )
}