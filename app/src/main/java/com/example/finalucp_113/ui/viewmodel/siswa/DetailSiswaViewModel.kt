package com.example.finalucp_113.ui.viewmodel.siswa


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.model.Siswa
import com.example.finalucp_113.repository.SiswaRepository
import com.example.finalucp_113.ui.view.siswa.DestinasiDetailSiswa
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailsiswaUiState{
    data class Success(val siswa: Siswa) : DetailsiswaUiState()
    object Error : DetailsiswaUiState()
    object Loading : DetailsiswaUiState()
}

class DetailsiswaViewModel(
    savedStateHandle: SavedStateHandle,
    private val siswaRepository: SiswaRepository
) : ViewModel() {

    private val id_siswa: String = checkNotNull(savedStateHandle[DestinasiDetailSiswa.id_siswa])
    var detailsiswaUiState: DetailsiswaUiState by mutableStateOf(DetailsiswaUiState.Loading)
        private set

    init {
        getsiswabyId()
    }

    fun getsiswabyId(){
        viewModelScope.launch {
            detailsiswaUiState = DetailsiswaUiState.Loading
            detailsiswaUiState = try {
                DetailsiswaUiState.Success(siswa = siswaRepository.getSiswaByidSiswa(id_siswa))
            } catch (e: IOException) {
                DetailsiswaUiState.Error
            }
        }
    }
}