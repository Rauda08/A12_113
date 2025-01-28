package com.example.finalucp_113.repository

import com.example.finalucp_113.model.Pendaftaran
import com.example.finalucp_113.service.PendaftaranService
import java.io.IOException



interface PendaftaranRepository {
    suspend fun getPendaftaran(): List<Pendaftaran>
    suspend fun insertPendaftaran(pendaftaran: Pendaftaran)
    suspend fun updatePendaftaran(idPendaftaran : String, pendaftaran: Pendaftaran)
    suspend fun deletePendaftaran(idPendaftaran: String)
    suspend fun getPendaftaranByidPendaftaran(idPendaftaran: String): Pendaftaran

}

class NetworkPendaftaranRepository(
    private val pendaftaranApiService: PendaftaranService
) : PendaftaranRepository {
    override suspend fun insertPendaftaran(pendaftaran: Pendaftaran) {
        pendaftaranApiService.insertPendaftaran(pendaftaran)
    }

//    override suspend fun insertPendaftaran(pendaftaran: Pendaftaran) {
//        val response = pendaftaranApiService.insertPendaftaran(pendaftaran)
//        if (response.isSuccessful) {
//            val responseBody = response.body()
//            if (responseBody != null) {
//                val insertedPendaftaran = Pendaftaran(
//                    id_pendaftaran = responseBody.id_pendaftaran,
//                    id_siswa = pendaftaran.id_siswa,
//                    nama_siswa = responseBody.nama_siswa,
//                    nama_kursus = responseBody.nama_kursus,
//                    id_kursus = pendaftaran.id_kursus,
//                    tanggal_pendaftaran = pendaftaran.tanggal_pendaftaran
//                )
//            }
//        } else {
//            throw IOException("Failed to insert Pendaftaran. HTTP status code: ${response.code}")
//        }
//    }

    override suspend fun updatePendaftaran(idPendaftaran: String, pendaftaran: Pendaftaran) {
        pendaftaranApiService.updatePendaftaran(idPendaftaran, pendaftaran)
    }

    override suspend fun deletePendaftaran(idPendaftaran: String) {
        try {
            val response = pendaftaranApiService.deletePendaftaran(idPendaftaran)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Pendaftaran. HTTP status code : ${response.code()} ")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPendaftaran(): List<Pendaftaran> = pendaftaranApiService.getPendaftaran()
    override suspend fun getPendaftaranByidPendaftaran(idPendaftaran: String): Pendaftaran {
        return pendaftaranApiService.getPendaftaranByIdPendaftaran(idPendaftaran)
    }
}


