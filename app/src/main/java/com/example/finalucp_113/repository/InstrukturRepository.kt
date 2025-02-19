package com.example.finalucp_113.repository

import com.example.finalucp_113.model.Instruktur
import com.example.finalucp_113.service.InstrukturService
import java.io.IOException

interface InstrukturRepository {
    suspend fun getInstruktur(): List<Instruktur>
    suspend fun insertInstruktur(instruktur: Instruktur)
    suspend fun updateInstruktur(idInstruktur : String, instruktur: Instruktur)
    suspend fun deleteInstruktur(idInstruktur: String)
    suspend fun getInstrukturByidInstruktur(idInstruktur: String): Instruktur
}

class NetworkInstrukturRepository(
    private val instrukturApiService: InstrukturService
) : InstrukturRepository {
    override suspend fun insertInstruktur(instruktur: Instruktur) {
        instrukturApiService.insertInstruktur(instruktur)
    }

    override suspend fun updateInstruktur(idInstruktur: String, instruktur: Instruktur) {
        instrukturApiService.updateInstruktur(idInstruktur, instruktur)
    }

    override suspend fun deleteInstruktur(idInstruktur: String) {
        try {
            val response = instrukturApiService.deleteInstruktur(idInstruktur)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Instruktur. HTTP status code : ${response.code()} ")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getInstruktur(): List<Instruktur> = instrukturApiService.getInstruktur()
    override suspend fun getInstrukturByidInstruktur(idInstruktur: String): Instruktur {
        return instrukturApiService.getInstrukturByIdInstruktur(idInstruktur)
    }
}