package com.example.finalucp_113.ui.viewmodel.pendaftaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.model.Pendaftaran
import com.example.finalucp_113.repository.PendaftaranRepository
import kotlinx.coroutines.launch

class InsertPendaftaranViewModel(private val pendaftaranRepository: PendaftaranRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertPendaftaranUIState())

    fun updateState(insertPendaftaranEvent: InsertPendaftaranEvent) {
        uiState = uiState.copy(
            pendaftaranEvent = insertPendaftaranEvent
        )
    }

    private fun validateFields(): Boolean {
        val event = uiState.pendaftaranEvent
        val errorState = FormErrorState(

            id_pendaftaran = if (event.id_pendaftaran.isNotEmpty()) null else "ID Pendaftaran tidak boleh kosong",
            id_siswa = if (event.id_siswa.isNotEmpty()) null else "ID Siswa tidak boleh kosong",
            id_kursus = if (event.id_kursus.isNotEmpty())  null else "ID Kursus tidak boleh kosong",
            tanggal_pendaftaran = if (event.tanggal_pendaftaran.isNotEmpty()) null else "Tanggal pendaftaran tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = uiState.pendaftaranEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    pendaftaranRepository.insertPendaftaran(currentEvent.toPendaftaran())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil Disimpan",
                        pendaftaranEvent = InsertPendaftaranEvent(),  // Reset input form
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

data class InsertPendaftaranUIState(
    val pendaftaranEvent: InsertPendaftaranEvent = InsertPendaftaranEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null
)

data class  InsertPendaftaranEvent (
    val  id_pendaftaran: String =  "" ,
    val  id_siswa :  String  =  "" ,
    val  id_kursus :  String  =  "" ,
    val  nama_siswa: String = "",
    val  nama_kursus : String = "",
    val  kategori : String = "",
    val  tanggal_pendaftaran :  String  =  ""
) {


}

data class FormErrorState(
    val id_pendaftaran: String? = null,
    val id_siswa: String? = null,
    val id_kursus: String? = null,
    val tanggal_pendaftaran: String? = null
) {
    fun isValid(): Boolean {
        return id_pendaftaran == null && id_siswa == null && id_kursus == null && tanggal_pendaftaran == null
    }
}
fun InsertPendaftaranEvent.toPendaftaran(): Pendaftaran = Pendaftaran(
    id_pendaftaran = id_pendaftaran,
    id_siswa = id_siswa,
    id_kursus = id_kursus,
    nama_siswa = nama_siswa,
    nama_kursus = nama_kursus,
    kategori = kategori,
    tanggal_pendaftaran = tanggal_pendaftaran
)
fun Pendaftaran. toUiStatePendaftaran () :  InsertPendaftaranUIState  =  InsertPendaftaranUIState (
    pendaftaranEvent =  toInsertPendaftaranUiEvent ()
)

fun Pendaftaran. toInsertPendaftaranUiEvent () :  InsertPendaftaranEvent  =  InsertPendaftaranEvent (
    id_pendaftaran =  id_pendaftaran,
    id_siswa = id_siswa,
    id_kursus = id_kursus,
    tanggal_pendaftaran = tanggal_pendaftaran
)
