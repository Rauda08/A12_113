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

    var pendaftaranUIState: PendaftaranUiState by mutableStateOf(PendaftaranUiState.Loading) // Inisialisasi dengan Loading
        private set

    private var originalPendaftaranList: List<Pendaftaran> = emptyList()

    init {
        getpendaftaran()
    }
    fun filterPendaftaran(category: String, query: String) {
        val filteredPendaftaran = if (category.isNotEmpty()) {
            originalPendaftaranList.filter {
                it.kategori.contains(category, ignoreCase = true) &&
                        (it.nama_siswa.contains(query, ignoreCase = true) || it.kategori.contains(query, ignoreCase = true))
            }
        } else {
            originalPendaftaranList.filter {
                it.nama_siswa.contains(query, ignoreCase = true) ||
                        it.kategori.contains(query, ignoreCase = true)
            }
        }
        pendaftaranUIState = PendaftaranUiState.Success(filteredPendaftaran)
    }

    fun getpendaftaran() {
        viewModelScope.launch {
            pendaftaranUIState = PendaftaranUiState.Loading
            try {
                val pendaftaranList = pendaftaranRepository.getPendaftaran()
                originalPendaftaranList = pendaftaranList
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
