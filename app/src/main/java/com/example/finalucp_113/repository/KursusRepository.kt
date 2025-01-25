package com.example.finalucp_113.repository

import com.example.finalucp_113.model.Kursus
import com.example.finalucp_113.service.KursusService
import java.io.IOException

interface KursusRepository {
    suspend fun getKursus(): List<Kursus>
    suspend fun insertKursus(kursus: Kursus)
    suspend fun updateKursus(idKursus : String, kursus: Kursus)
    suspend fun deleteKursus(idKursus: String)
    suspend fun getKursusByidKursus(idKursus: String): Kursus
}

class NetworkKursusRepository(
    private val kursusApiService: KursusService
) : KursusRepository {
    override suspend fun insertKursus(kursus: Kursus) {
        kursusApiService.insertKursus(kursus)
    }

    override suspend fun updateKursus(idKursus: String, kursus: Kursus) {
        kursusApiService.updateKursus(idKursus,kursus)
    }

    override suspend fun deleteKursus(idKursus: String) {
        try {
            val response = kursusApiService.deleteKursus(idKursus)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete kursus. HTTP status code : ${response.code()} ")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception){
            throw e
        }
    }

    override suspend fun getKursus(): List<Kursus> = kursusApiService.getKursus()
    override suspend fun getKursusByidKursus(idKursus: String): Kursus {
        return kursusApiService.getKursusByIdKursus(idKursus)
    }
}