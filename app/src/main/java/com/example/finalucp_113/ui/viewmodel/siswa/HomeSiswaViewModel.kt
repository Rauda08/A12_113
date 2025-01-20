package com.example.finalucp_113.ui.viewmodel.siswa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.model.Siswa
import com.example.finalucp_113.repository.SiswaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class  HomeUiState  {
    data class  Success ( val  siswa :  List <Siswa> )  :  HomeUiState ()
    object  Error  :  HomeUiState ()
    object  Loading  :  HomeUiState ()
}

class  HomeViewModel ( private val  siswa : SiswaRepository)  :  ViewModel() {
    var  siswaUIState :  HomeUiState  by  mutableStateOf ( HomeUiState . Loading )
        private set

    init  {
        getSiswa()
    }

    fun  getSiswa () {
        viewModelScope . launch  {
            siswaUIState  =  HomeUiState . Loading
            siswaUIState  = try  {
                HomeUiState . Success (siswa. getSiswa ())
            }  catch  (e : IOException) {
                HomeUiState . Error
            }  catch  (e : coil.network.HttpException) {
                HomeUiState . Error
            }
        }
    }


    fun  deleteSiswa ( idSiswa :  Int ) {
        viewModelScope . launch  {
            try  {
                siswa. deleteSiswa ( idSiswa )
            }  catch  (e : IOException){
                HomeUiState . Error
            }  catch  (e : coil.network.HttpException){
                HomeUiState . Error
            }
        }
    }
}