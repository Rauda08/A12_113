package com.example.finalucp_113.ui.viewmodel.instruktur

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.model.Instruktur
import com.example.finalucp_113.repository.InstrukturRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class InstrukturUiState {
    data class Success(val instruktur: List<Instruktur>) : InstrukturUiState()
    object Error : InstrukturUiState()
    object Loading : InstrukturUiState()
}

class HomeInstrukturViewModel(private val instrukturRepository: InstrukturRepository) : ViewModel() {

    var instrukturUiState: InstrukturUiState by mutableStateOf(InstrukturUiState.Success(emptyList())) // Inisialisasi dengan Success kosong
        private set

    init {
        if ((instrukturUiState as? InstrukturUiState.Success)?.instruktur?.isEmpty() == true) {
            getInstruktur()
        }
    }

    fun getInstruktur() {
        viewModelScope.launch {
            instrukturUiState = InstrukturUiState.Loading
            instrukturUiState = try {
                val instrukturList = instrukturRepository.getInstruktur()
                InstrukturUiState.Success(instrukturList)
            } catch (e: IOException) {
                InstrukturUiState.Error
            } catch (e: HttpException) {
                InstrukturUiState.Error
            }
        }
    }

    fun  deleteinstruktur ( id_instruktur :  String ) {
        viewModelScope . launch  {
            try  {
                instrukturRepository. deleteInstruktur ( id_instruktur)
            }  catch  (e : IOException){
                InstrukturUiState . Error
            }  catch  (e : HttpException){
                InstrukturUiState . Error
            }
        }
    }
}
