package com.example.finalucp_113.service

import com.example.finalucp_113.model.Kursus
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface KursusService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("getkursus.php")
    suspend fun getKursus(): List<Kursus>

    @GET("getbyidKursus.php/{id_kursus}")
    suspend fun getKursusByIdKursus(@Query("id_kursus") idKursus: Int): Kursus

    @POST("insertKursus.php")
    suspend fun insertKursus(@Body kursus: Kursus)

    @PUT("editKursus.php/{id_kursus}")
    suspend fun updateKursus(@Query("id_kursus") idKursus: Int, @Body kursus: Kursus)

    @DELETE("deleteKursus.php/{id_kursus}")
    suspend fun deleteKursus(@Query("id_kursus") idKursus: Int): Response<Void>
}