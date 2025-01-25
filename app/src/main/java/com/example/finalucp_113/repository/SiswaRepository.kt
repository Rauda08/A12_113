package com.example.finalucp_113.repository

import com.example.finalucp_113.model.Siswa
import com.example.finalucp_113.service.SiswaService
import java.io.IOException

interface SiswaRepository{
    suspend fun getSiswa(): List<Siswa>
    suspend fun insertSiswa(siswa: Siswa)
    suspend fun updateSiswa(idSiswa : String, siswa: Siswa)
    suspend fun deleteSiswa(idSiswa: String)
    suspend fun getSiswaByidSiswa(idSiswa: String): Siswa
}

class NetworkSiswaRepository(
    private val siswaApiService: SiswaService
) : SiswaRepository {
    override suspend fun insertSiswa(siswa: Siswa) {
        siswaApiService.insertSiswa(siswa)
    }

    override suspend fun updateSiswa(idSiswa: String, siswa: Siswa) {
        siswaApiService.updateSiswa(idSiswa,siswa)
    }

    override suspend fun deleteSiswa(idSiswa: String) {
        try {
            val response = siswaApiService.deleteSiswa(idSiswa)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete siswa. HTTP status code : ${response.code()} ")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception){
            throw e
        }
    }

    override suspend fun getSiswa(): List<Siswa> = siswaApiService.getSiswa()
    override suspend fun getSiswaByidSiswa(idSiswa: String): Siswa {
        return siswaApiService.getSiswaByIdSiswa(idSiswa)
    }
}