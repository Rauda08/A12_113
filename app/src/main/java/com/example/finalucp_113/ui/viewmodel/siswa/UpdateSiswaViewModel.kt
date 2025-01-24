package com.example.finalucp_113.ui.viewmodel.siswa

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.model.Siswa
import com.example.finalucp_113.repository.SiswaRepository
import kotlinx.coroutines.launch

class UpdateSiswaViewModel(
    savedStateHandle: SavedStateHandle,
    private val siswaRepository: SiswaRepository
) : ViewModel() {

    var updateUIState by mutableStateOf(InsertSiswaUIState())
        private set

    private val id_siswa: String = checkNotNull(savedStateHandle[DestinasiUpdateSiswa.id_siswa])

    init {
        viewModelScope.launch {
            updateUIState = siswaRepository.getSiswa(id_siswa)
                .filterNotNull()
                .first()
                .toUIStateSiswa()
        }
    }

    fun updateState(siswaEvent: InsertSiswaEvent) {
        updateUIState = updateUIState.copy(siswaEvent = siswaEvent)
    }

    fun validateFields(): Boolean {
        val event = updateUIState.siswaEvent
        val errorState = FormErrorState(
            id_siswa = if (event.id_siswa.isNotEmpty()) null else "ID Siswa tidak boleh kosong",
            nama_siswa = if (event.nama_siswa.isNotEmpty()) null else "Nama Siswa tidak boleh kosong",
            email = if (event.email.isNotEmpty()) null else "Email tidak boleh kosong",
            no_telepon = if (event.no_telepon.isNotEmpty()) null else "Nomor Telepon tidak boleh kosong"
        )

        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateUIState.siswaEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    siswaRepository.updateSiswa(currentEvent.id_siswa, currentEvent.toSiswa())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data berhasil diperbarui",
                        siswaEvent = InsertSiswaEvent(), // Reset form
                        isEntryValid = FormErrorState() // Reset error state
                    )
                } catch (e: Exception) {
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Gagal mengupdate data: ${e.message}"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data tidak valid"
            )
        }
    }

    fun resetSnackBarMessage() {
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }
}

fun Siswa.toUIStateSiswa() : InsertSiswaUIState = InsertSiswaUIState(
    siswaEvent = this.toDetailUiEvent(),
)
