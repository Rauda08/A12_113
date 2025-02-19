package com.example.finalucp_113.model

import kotlinx.serialization.Serializable

@Serializable
data class Siswa(
    val id_siswa: String,
    val nama_siswa: String,
    val email: String,
    val no_telepon: String
)

@Serializable
data class Instruktur (
    val id_instruktur: String,
    val nama_instruktur: String,
    val email: String,
    val no_telepon : String,
    val deskripsi : String
)

@Serializable
data class Kursus(
    val id_kursus: String,
    val nama_kursus: String,
    val deskripsi: String,
    val kategori: String,
    val harga: String,
    val id_instruktur: String
)

@Serializable
data class Pendaftaran(
    val id_pendaftaran: String,
    val id_siswa: String,
    val nama_siswa: String = "",
    val nama_kursus: String = "",
    val id_kursus: String,
    val kategori: String = "",
    val tanggal_pendaftaran: String
)