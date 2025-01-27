package com.example.finalucp_113.ui.viewmodel.kursus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.model.Kursus
import com.example.finalucp_113.repository.KursusRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class KursusUiState {
    data class Success(val kursus: List<Kursus>) : KursusUiState()
    object Error : KursusUiState()
    object Loading : KursusUiState()
}

class HomeKursusViewModel(private val kursusRepository: KursusRepository) : ViewModel() {

    var kursusUIState: KursusUiState by mutableStateOf(KursusUiState.Loading) // Inisialisasi dengan Loading
        private set
    private var originalKursusList: List<Kursus> = emptyList() // Menyimpan daftar kursus asli

    init {
        getKursus()
    }

    fun searchKursus(query: String) {
        val filteredKursus = originalKursusList.filter {
            it.nama_kursus.contains(query, ignoreCase = true) ||
            it.kategori.contains(query, ignoreCase = true)
        }
        kursusUIState = KursusUiState.Success(filteredKursus)
    }

    fun getKursus() {
        viewModelScope.launch {
            kursusUIState = KursusUiState.Loading
            try {
                val kursusList = kursusRepository.getKursus()
                originalKursusList = kursusList // Menyimpan daftar kursus asli
                kursusUIState = KursusUiState.Success(kursusList)
            } catch (e: IOException) {
                kursusUIState = KursusUiState.Error
            } catch (e: HttpException) {
                kursusUIState = KursusUiState.Error
            }
        }
    }

    fun deletekursus(id_kursus: String) {
        viewModelScope.launch {
            try {
                kursusRepository.deleteKursus(id_kursus)
                getKursus() // Memuat ulang kursus setelah penghapusan
            } catch (e: IOException) {
                kursusUIState = KursusUiState.Error
            } catch (e: HttpException) {
                kursusUIState = KursusUiState.Error
            }
        }
    }
}

