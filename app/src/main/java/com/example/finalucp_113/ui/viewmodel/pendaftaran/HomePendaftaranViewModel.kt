package com.example.finalucp_113.ui.viewmodel.pendaftaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.model.Pendaftaran
import com.example.finalucp_113.repository.PendaftaranRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class PendaftaranUiState {
    data class Success(val pendaftaran: List<Pendaftaran>) : PendaftaranUiState()
    object Error : PendaftaranUiState()
    object Loading : PendaftaranUiState()
}

class HomePendaftaranViewModel(private val pendaftaranRepository: PendaftaranRepository) : ViewModel() {

    var pendaftaranUIState: PendaftaranUiState by mutableStateOf(PendaftaranUiState.Success(emptyList()))
        private set

    init {
        // Cek apakah sudah ada data pendaftaran sebelum memuatnya
        if ((pendaftaranUIState as? PendaftaranUiState.Success)?.pendaftaran?.isEmpty() == true) {
            getpendaftaran()
        }
    }

    fun getpendaftaran() {
        viewModelScope.launch {
            pendaftaranUIState = PendaftaranUiState.Loading
            pendaftaranUIState = try {
                val pendaftaranList = pendaftaranRepository.getPendaftaran()
                PendaftaranUiState.Success(pendaftaranList)
            } catch (e: IOException) {
                PendaftaranUiState.Error
            } catch (e: HttpException) {
                PendaftaranUiState.Error
            }
        }
    }

    fun  deletependaftaran ( id_pendaftaran :  String ) {
        viewModelScope . launch  {
            try  {
                pendaftaranRepository. deletePendaftaran ( id_pendaftaran )
            }  catch  (e : IOException){
                PendaftaranUiState . Error
            }  catch  (e : HttpException){
                PendaftaranUiState . Error
            }
        }
    }
}
