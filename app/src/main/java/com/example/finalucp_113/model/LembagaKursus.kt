package com.example.finalucp_113.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Siswa (
    @SerialName("idSiswa")
    val id_siswa : Int,

    val nama_siswa : String,
    val email : String,
    val no_telepon : String

)

@Serializable
data class Instruktur (
    @SerialName("idInstruktur")
    val id_instruktur: Int,

    val nama_instruktur: String,
    val email: String,
    val nomor_telepon : String,
    val deskripsi : String
)

@Serializable
data class Kursus (
    @SerialName("idKursus")
    val id_kursus: Int,

    val nama_kursus: String,
    val deskripsi: String,
    val kategori : Kategori,
    val harga : Double,
    val id_instruktur : Instruktur
)

@Serializable
enum class Kategori {
    Saintek,
    Soshum
}

@Serializable
data class Pendaftaran (
    @SerialName("idPendaftaran")
    val id_pendaftaran: Int,

    val id_siswa: Siswa,
    val id_kursus: Kursus,
    val tanggal_pendaftaran : String
)