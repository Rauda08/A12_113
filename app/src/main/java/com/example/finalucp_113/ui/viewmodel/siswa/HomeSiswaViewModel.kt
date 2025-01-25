package com.example.finalucp_113.ui.viewmodel.siswa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.model.Siswa
import com.example.finalucp_113.repository.SiswaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class SiswaUiState {
    data class Success(val siswa: List<Siswa>) : SiswaUiState()
    object Error : SiswaUiState()
    object Loading : SiswaUiState()
}

class HomeSiswaViewModel(private val siswaRepository: SiswaRepository) : ViewModel() {

    var siswaUIState: SiswaUiState by mutableStateOf(SiswaUiState.Success(emptyList())) // Inisialisasi dengan Success kosong
        private set

    init {
        // Cek apakah sudah ada data siswa sebelum memuatnya
        if ((siswaUIState as? SiswaUiState.Success)?.siswa?.isEmpty() == true) {
            getSiswa()
        }
    }

    fun getSiswa() {
        viewModelScope.launch {
            siswaUIState = SiswaUiState.Loading
            siswaUIState = try {
                val siswaList = siswaRepository.getSiswa()
                SiswaUiState.Success(siswaList)
            } catch (e: IOException) {
                SiswaUiState.Error
            } catch (e: HttpException) {
                SiswaUiState.Error
            }
        }
    }

    fun  deletesiswa ( id_siswa :  String ) {
        viewModelScope . launch  {
            try  {
                siswaRepository. deleteSiswa ( id_siswa )
            }  catch  (e : IOException){
                SiswaUiState . Error
            }  catch  (e : HttpException){
                SiswaUiState . Error
            }
        }
    }
}
