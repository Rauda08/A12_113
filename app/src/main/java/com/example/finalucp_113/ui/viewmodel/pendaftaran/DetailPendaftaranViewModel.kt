package com.example.finalucp_113.ui.viewmodel.pendaftaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_113.model.Pendaftaran
import com.example.finalucp_113.repository.KursusRepository
import com.example.finalucp_113.repository.SiswaRepository
import com.example.finalucp_113.repository.PendaftaranRepository
import com.example.finalucp_113.ui.view.pendaftaran.DestinasiDetailPendaftaran
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailpendaftaranUiState{
    data class Success(val pendaftaran: Pendaftaran) : DetailpendaftaranUiState()
    object Error : DetailpendaftaranUiState()
    object Loading : DetailpendaftaranUiState()
}

class DetailpendaftaranViewModel(
    savedStateHandle: SavedStateHandle,
    private val pendaftaranRepository: PendaftaranRepository,
    private val kursusRepository: KursusRepository,
    private val siswaRepository: SiswaRepository
) : ViewModel() {

    private val id_pendaftaran: String = checkNotNull(savedStateHandle[DestinasiDetailPendaftaran.id_pendaftaran])
    var detailpendaftaranUiState: DetailpendaftaranUiState by mutableStateOf(DetailpendaftaranUiState.Loading)
        private set

    init {
        getpendaftaranbyId()
    }

    fun getpendaftaranbyId(){
        viewModelScope.launch {
            detailpendaftaranUiState = DetailpendaftaranUiState.Loading
            detailpendaftaranUiState = try {
                val pendaftaran = pendaftaranRepository.getPendaftaranByidPendaftaran(id_pendaftaran)

                if (pendaftaran.nama_siswa.isEmpty() || pendaftaran.nama_kursus.isEmpty()){
                    val namaSiswa = getNamaSiswaById(pendaftaran.id_siswa)
                    val namaKursus = getNamaKursusById(pendaftaran.id_kursus)

                    val updatePendaftaran = pendaftaran.copy(
                        nama_siswa = namaSiswa,
                        nama_kursus = namaKursus
                    )

                    DetailpendaftaranUiState.Success(pendaftaran=updatePendaftaran)
                }
                else{
                    DetailpendaftaranUiState.Success(pendaftaran = pendaftaran)
                }
                //DetailpendaftaranUiState.Success(pendaftaran = pendaftaranRepository.getPendaftaranByidPendaftaran(id_pendaftaran))
            } catch (e: IOException) {
                DetailpendaftaranUiState.Error
            }
        }
    }

    private suspend fun getNamaKursusById(idKursus: String): String {
        return kursusRepository.getKursusByidKursus(idKursus).nama_kursus
    }

    private suspend fun getNamaSiswaById(idSiswa: String): String {
        return siswaRepository.getSiswaByidSiswa(idSiswa).nama_siswa
    }
}
fun Pendaftaran.toDetailUiEvent () : InsertPendaftaranEvent {
    return InsertPendaftaranEvent(
        id_pendaftaran = id_pendaftaran,
        id_siswa = id_siswa,
        nama_siswa = nama_siswa,
        nama_kursus = nama_kursus,
        id_kursus = id_kursus,
        tanggal_pendaftaran = tanggal_pendaftaran
    )
}
