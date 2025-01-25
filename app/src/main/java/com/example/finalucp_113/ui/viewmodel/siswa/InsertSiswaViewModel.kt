package com.example.finalucp_113.ui.viewmodel.siswa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.model.Siswa
import com.example.finalucp_113.repository.SiswaRepository
import kotlinx.coroutines.launch

class InsertSiswaViewModel(private val siswaRepository: SiswaRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertSiswaUIState())

    fun updateState(insertSiswaEvent: InsertSiswaEvent) {
        uiState = uiState.copy(
            siswaEvent = insertSiswaEvent
        )
    }

    private fun validateFields(): Boolean {
        val event = uiState.siswaEvent
        val errorState = FormErrorState(

            id_siswa = if (event.id_siswa.isNotEmpty()) null else "ID Siswa tidak boleh kosong",
            nama_siswa = if (event.nama_siswa.isNotEmpty()) null else "Nama tidak boleh kosong",
            email = if (event.email.isNotEmpty())  null else "Email tidak boleh kosong",
            no_telepon = if (event.no_telepon.isNotEmpty()) null else "No Telepon tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = uiState.siswaEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    siswaRepository.insertSiswa(currentEvent.toSiswa())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil Disimpan",
                        siswaEvent = InsertSiswaEvent(),  // Reset input form
                        isEntryValid = FormErrorState() // Reset error state
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal Disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Data tidak valid. Periksa kembali data anda"
            )
        }
    }

    fun resetSnackBarMessage() {
        uiState = uiState.copy(
            snackBarMessage = null
        )
    }
}

data class InsertSiswaUIState(
    val siswaEvent: InsertSiswaEvent = InsertSiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null
)

data class  InsertSiswaEvent (
    val  id_siswa: String =  "" ,
    val  nama_siswa :  String  =  "" ,
    val  email :  String  =  "" ,
    val  no_telepon :  String  =  ""
)

data class FormErrorState(
    val id_siswa: String? = null,
    val nama_siswa: String? = null,
    val email: String? = null,
    val no_telepon: String? = null
) {
    fun isValid(): Boolean {
        return id_siswa == null && nama_siswa == null && email == null && no_telepon == null
    }
}
fun InsertSiswaEvent.toSiswa(): Siswa = Siswa(
    id_siswa = id_siswa,
    nama_siswa = nama_siswa,
    email = email,
    no_telepon = no_telepon
)
fun  Siswa . toUiStateSiswa () :  InsertSiswaUIState  =  InsertSiswaUIState (
    siswaEvent =  toInsertSiswaUiEvent ()
)

fun  Siswa . toInsertSiswaUiEvent () :  InsertSiswaEvent  =  InsertSiswaEvent (
    id_siswa =  id_siswa,
    nama_siswa =  nama_siswa,
    email =  email,
    no_telepon =  no_telepon
)
