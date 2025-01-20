package com.example.finalucp_113.repository

import com.example.finalucp_113.model.Pendaftaran
import com.example.finalucp_113.service.PendaftaranService
import java.io.IOException

interface PendaftaranRepository {
    suspend fun getPendaftaran(): List<Pendaftaran>
    suspend fun insertPendaftaran(pendaftaran: Pendaftaran)
    suspend fun updatePendaftaran(idPendaftaran : Int, pendaftaran: Pendaftaran)
    suspend fun deletePendaftaran(idPendaftaran: Int)
    suspend fun getPendaftaranByidPendaftaran(idPendaftaran: Int): Pendaftaran
}

class NetworkPendaftaranRepository(
    private val pendaftaranApiService: PendaftaranService
) : PendaftaranRepository {
    override suspend fun insertPendaftaran(pendaftaran: Pendaftaran) {
        pendaftaranApiService.insertPendaftaran(pendaftaran)
    }

    override suspend fun updatePendaftaran(idPendaftaran: Int, pendaftaran: Pendaftaran) {
        pendaftaranApiService.updatePendaftaran(idPendaftaran, pendaftaran)
    }

    override suspend fun deletePendaftaran(idPendaftaran: Int) {
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
    override suspend fun getPendaftaranByidPendaftaran(idPendaftaran: Int): Pendaftaran {
        return pendaftaranApiService.getPendaftaranByIdPendaftaran(idPendaftaran)
    }
}