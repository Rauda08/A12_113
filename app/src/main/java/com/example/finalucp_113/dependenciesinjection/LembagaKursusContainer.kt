package com.example.finalucp_113.dependenciesinjection

import com.example.finalucp_113.repository.InstrukturRepository
import com.example.finalucp_113.repository.KursusRepository
import com.example.finalucp_113.repository.NetworkInstrukturRepository
import com.example.finalucp_113.repository.NetworkKursusRepository
import com.example.finalucp_113.repository.NetworkPendaftaranRepository
import com.example.finalucp_113.repository.NetworkSiswaRepository
import com.example.finalucp_113.repository.PendaftaranRepository
import com.example.finalucp_113.repository.SiswaRepository
import com.example.finalucp_113.service.InstrukturService
import com.example.finalucp_113.service.KursusService
import com.example.finalucp_113.service.PendaftaranService
import com.example.finalucp_113.service.SiswaService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val siswaRepository: SiswaRepository
    val kursusRepository: KursusRepository
    val instrukturRepository: InstrukturRepository
    val pendaftaranRepository: PendaftaranRepository
}

abstract class BaseContainer {
    protected val baseUrl = "http://192.168.1.11/lembagaKursus/"
    protected val json = Json { ignoreUnknownKeys = true }
    protected val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()
}

class SiswaContainer : BaseContainer(), AppContainer {
    private val siswaService: SiswaService by lazy {
        retrofit.create(SiswaService::class.java)
    }

    override val siswaRepository: SiswaRepository by lazy {
        NetworkSiswaRepository(siswaService)
    }

    override val kursusRepository: KursusRepository
        get() = throw NotImplementedError("KursusRepository belum diimplementasikan")

    override val instrukturRepository: InstrukturRepository
        get() = throw NotImplementedError("InstrukturRepository belum diimplementasikan")

    override val pendaftaranRepository: PendaftaranRepository
        get() = throw NotImplementedError("PendaftaranRepository belum diimplementasikan")
}

class InstrukturContainer : BaseContainer(), AppContainer {
    private val instrukturService: InstrukturService by lazy {
        retrofit.create(InstrukturService::class.java)
    }

    override val instrukturRepository: InstrukturRepository by lazy {
        NetworkInstrukturRepository(instrukturService)
    }

    override val siswaRepository: SiswaRepository
        get() = throw NotImplementedError("SiswaRepository belum diimplementasikan")

    override val kursusRepository: KursusRepository
        get() = throw NotImplementedError("KursusRepository belum diimplementasikan")

    override val pendaftaranRepository: PendaftaranRepository
        get() = throw NotImplementedError("PendaftaranRepository belum diimplementasikan")
}

class KursusContainer : BaseContainer(), AppContainer {
    private val kursusService: KursusService by lazy {
        retrofit.create(KursusService::class.java)
    }

    override val kursusRepository: KursusRepository by lazy {
        NetworkKursusRepository(kursusService)
    }

    override val siswaRepository: SiswaRepository
        get() = throw NotImplementedError("SiswaRepository belum diimplementasikan")

    override val instrukturRepository: InstrukturRepository
        get() = throw NotImplementedError("InstrukturRepository belum diimplementasikan")

    override val pendaftaranRepository: PendaftaranRepository
        get() = throw NotImplementedError("PendaftaranRepository belum diimplementasikan")
}

class PendaftaranContainer : BaseContainer(), AppContainer {
    private val pendaftaranService: PendaftaranService by lazy {
        retrofit.create(PendaftaranService::class.java)
    }

    override val pendaftaranRepository: PendaftaranRepository by lazy {
        NetworkPendaftaranRepository(pendaftaranService)
    }

    override val siswaRepository: SiswaRepository
        get() = throw NotImplementedError("SiswaRepository belum diimplementasikan")

    override val kursusRepository: KursusRepository
        get() = throw NotImplementedError("KursusRepository belum diimplementasikan")

    override val instrukturRepository: InstrukturRepository
        get() = throw NotImplementedError("InstrukturRepository belum diimplementasikan")
}
