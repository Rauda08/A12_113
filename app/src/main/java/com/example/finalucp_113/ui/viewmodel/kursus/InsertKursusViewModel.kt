package com.example.finalucp_113.ui.viewmodel.kursus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.model.Kursus
import com.example.finalucp_113.repository.KursusRepository
import kotlinx.coroutines.launch

class InsertKursusViewModel(private val kursusRepository: KursusRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertKursusUIState())

    fun updateState(insertKursusEvent: InsertKursusEvent) {
        uiState = uiState.copy(
            kursusEvent = insertKursusEvent
        )
    }

    internal fun validateFields(): Boolean {
        val event = uiState.kursusEvent
        var errors = FormErrorState()
        var isValid = true


        val harga = event.harga.toIntOrNull()
        if (harga == null || harga <= 0) {
            errors = errors.copy(harga = "Harga harus berupa angka")
            isValid = false
        }


        errors = errors.copy(
            id_kursus = if (event.id_kursus.isNotEmpty()) null else "ID Kursus tidak boleh kosong",
            nama_kursus = if (event.nama_kursus.isNotEmpty()) null else "Nama tidak boleh kosong",
            deskripsi = if (event.deskripsi.isNotEmpty()) null else "Deskripsi tidak boleh kosong",
            kategori = if (event.kategori.isNotEmpty()) null else "Kategori tidak boleh kosong",
            harga = if (event.harga.isNotEmpty()) null else "Harga tidak boleh kosong",
            id_instruktur = if (event.id_instruktur.isNotEmpty()) null else "ID Instruktur tidak boleh kosong"
        )


        uiState = uiState.copy(isEntryValid = errors)

        return isValid && errors.isValid()
    }

    fun saveData(onSuccess: () -> Unit) {
        val currentEvent = uiState.kursusEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    // Proses penyimpanan data
                    kursusRepository.insertKursus(currentEvent.toKursus())

                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil Disimpan",
                        kursusEvent = InsertKursusEvent(),  // Reset input form
                        isEntryValid = FormErrorState() // Reset error state
                    )
                    onSuccess()
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

data class InsertKursusUIState(
    val kursusEvent: InsertKursusEvent = InsertKursusEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null
)

data class InsertKursusEvent(
    val id_kursus: String = "",
    val nama_kursus: String = "",
    val deskripsi: String = "",
    val kategori: String = "",
    val harga: String = "",
    val id_instruktur: String = ""
)

data class FormErrorState(
    val id_kursus: String? = null,
    val nama_kursus: String? = null,
    val deskripsi: String? = null,
    val kategori: String? = null,
    var harga: String? = null,
    val id_instruktur: String? = null
) {
    fun isValid(): Boolean {
        return id_kursus == null && nama_kursus == null && deskripsi == null && kategori == null && harga == null && id_instruktur == null
    }
}

fun InsertKursusEvent.toKursus(): Kursus = Kursus(
    id_kursus = id_kursus,
    nama_kursus = nama_kursus,
    deskripsi = deskripsi,
    kategori = kategori,
    harga = harga,
    id_instruktur = id_instruktur,
)

fun Kursus.toUiStateKursus(): InsertKursusUIState = InsertKursusUIState(
    kursusEvent = toInsertKursusUiEvent()
)

fun Kursus.toInsertKursusUiEvent(): InsertKursusEvent = InsertKursusEvent(
    id_kursus = id_kursus,
    nama_kursus = nama_kursus,
    deskripsi = deskripsi,
    kategori = kategori,
    harga = harga,
    id_instruktur = id_instruktur
)
