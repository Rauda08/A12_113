package com.example.finalucp_113.ui.viewmodel.pendaftaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.model.Pendaftaran
import com.example.finalucp_113.repository.PendaftaranRepository
import com.example.finalucp_113.ui.view.pendaftaran.DestinasiDetailPendaftaran
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailpendaftaranUiState{
    data class Success(val pendaftaran: Pendaftaran) : DetailpendaftaranUiState()
    object Error : DetailpendaftaranUiState()
    object Loading : DetailpendaftaranUiState()
}

class DetailpendaftaranViewModel(
    savedStateHandle: SavedStateHandle,
    private val pendaftaranRepository: PendaftaranRepository
) : ViewModel() {

    private val id_pendaftaran: String = checkNotNull(savedStateHandle[DestinasiDetailPendaftaran.id_pendaftaran])
    var detailpendaftaranUiState: DetailpendaftaranUiState by mutableStateOf(DetailpendaftaranUiState.Loading)
        private set

    init {
        getpendaftaranbyId()
    }

    fun getpendaftaranbyId(){
        viewModelScope.launch {
            detailpendaftaranUiState = DetailpendaftaranUiState.Loading
            detailpendaftaranUiState = try {
                DetailpendaftaranUiState.Success(pendaftaran = pendaftaranRepository.getPendaftaranByidPendaftaran(id_pendaftaran))
            } catch (e: IOException) {
                DetailpendaftaranUiState.Error
            }
        }
    }
}
fun Pendaftaran.toDetailUiEvent () : InsertPendaftaranEvent {
    return InsertPendaftaranEvent(
        id_pendaftaran = id_pendaftaran,
        id_siswa = id_siswa,
        id_kursus = id_kursus,
        tanggal_pendaftaran = tanggal_pendaftaran
    )
}
