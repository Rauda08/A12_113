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

    var kursusUIState: KursusUiState by mutableStateOf(KursusUiState.Success(emptyList())) // Inisialisasi dengan Success kosong
        private set

    init {
        if ((kursusUIState as? KursusUiState.Success)?.kursus?.isEmpty() == true) {
            getKursus()
        }
    }

    fun getKursus() {
        viewModelScope.launch {
            kursusUIState = KursusUiState.Loading
            kursusUIState = try {
                val kursusList = kursusRepository.getKursus()
                KursusUiState.Success(kursusList)
            } catch (e: IOException) {
                KursusUiState.Error
            } catch (e: HttpException) {
                KursusUiState.Error
            }
        }
    }

    fun  deletekursus ( id_kursus :  String ) {
        viewModelScope . launch  {
            try  {
                kursusRepository. deleteKursus ( id_kursus )
            }  catch  (e : IOException){
                KursusUiState . Error
            }  catch  (e : HttpException){
                KursusUiState . Error
            }
        }
    }
}
