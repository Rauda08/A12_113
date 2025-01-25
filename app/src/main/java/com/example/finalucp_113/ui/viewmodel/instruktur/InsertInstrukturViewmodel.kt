package com.example.finalucp_113.ui.viewmodel.instruktur

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.model.Instruktur
import com.example.finalucp_113.repository.InstrukturRepository
import kotlinx.coroutines.launch

class InsertInstrukturViewmodel(private val instrukturRepository: InstrukturRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertInstrukturUIState())

    fun updateState(insertInstrukturEvent: InsertInstrukturEvent) {
        uiState = uiState.copy(
            instrukturEvent = insertInstrukturEvent
        )
    }

    private fun validateFields(): Boolean {
        val event = uiState.instrukturEvent
        val errorState = FormErrorState(

            id_instruktur = if (event.id_instruktur.isNotEmpty()) null else "ID Instruktur tidak boleh kosong",
            nama_instruktur = if (event.nama_instruktur.isNotEmpty()) null else "Nama tidak boleh kosong",
            email = if (event.email.isNotEmpty())  null else "Email tidak boleh kosong",
            no_telepon = if (event.no_telepon.isNotEmpty()) null else "No Telepon tidak boleh kosong",
            deskripsi = if (event.deskripsi.isNotEmpty()) null else "Deskripsi tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = uiState.instrukturEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    instrukturRepository.insertInstruktur(currentEvent.toInstruktur())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil Disimpan",
                        instrukturEvent = InsertInstrukturEvent(),  // Reset input form
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

data class InsertInstrukturUIState(
    val instrukturEvent: InsertInstrukturEvent = InsertInstrukturEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null
)

data class  InsertInstrukturEvent (
    val  id_instruktur: String =  "" ,
    val  nama_instruktur :  String  =  "" ,
    val  email :  String  =  "" ,
    val  no_telepon :  String  =  "",
    val  deskripsi :  String  =  ""
)

data class FormErrorState(
    val id_instruktur: String? = null,
    val nama_instruktur: String? = null,
    val email: String? = null,
    val no_telepon: String? = null,
    val deskripsi: String? = null,
) {
    fun isValid(): Boolean {
        return id_instruktur == null && nama_instruktur == null && email == null && no_telepon == null && deskripsi == null
    }
}

fun InsertInstrukturEvent.toInstruktur(): Instruktur = Instruktur(
    id_instruktur =  id_instruktur,
    nama_instruktur =  nama_instruktur,
    email =  email,
    no_telepon =  no_telepon,
    deskripsi = deskripsi
)
fun Instruktur. toUiStateInstruktur () :  InsertInstrukturUIState  =  InsertInstrukturUIState (
    instrukturEvent =  toInsertInstrukturUiEvent ()
)

fun Instruktur. toInsertInstrukturUiEvent () :  InsertInstrukturEvent  =  InsertInstrukturEvent (
    id_instruktur =  id_instruktur,
    nama_instruktur =  nama_instruktur,
    email =  email,
    no_telepon =  no_telepon,
    deskripsi = deskripsi
)
