package com.example.finalucp_113.dependenciesinjection

import com.example.finalucp_113.repository.NetworkSiswaRepository
import com.example.finalucp_113.repository.SiswaRepository
import com.example.finalucp_113.service.SiswaService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val siswaRepository: SiswaRepository
//    val kursusRepository: KursusRepository
//    val instrukturRepository: InstrukturRepository
//    val pendaftaranRepository: PendaftaranRepository
}

class LembagaKursusContainer : AppContainer {
    private val baseUrl = "http://192.168.88.48/ucpPAM/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val siswaService: SiswaService by lazy { retrofit.create(SiswaService::class.java) }
    override val siswaRepository: SiswaRepository by lazy { NetworkSiswaRepository(siswaService) }


}