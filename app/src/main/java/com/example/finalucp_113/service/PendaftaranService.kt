package com.example.finalucp_113.service

import com.example.finalucp_113.model.Kursus
import com.example.finalucp_113.model.Pendaftaran
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PendaftaranService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("getpendaftaran.php")
    suspend fun getPendaftaran(): List<Pendaftaran>
    @GET("getbyidpendaftaran.php/{id_pendaftaran}")
    suspend fun getPendaftaranByIdPendaftaran(@Query("id_pendaftaran") idPendaftaran: String): Pendaftaran

    @POST("insertpendaftaran.php")
    suspend fun insertPendaftaran(@Body pendaftaran: Pendaftaran)

    @PUT("editpendaftaran.php/{id_pendaftaran}")
    suspend fun updatePendaftaran(@Query("id_pendaftaran") idPendaftaran: String, @Body pendaftaran: Pendaftaran)

    @DELETE("deletependaftaran.php/{id_pendaftaran}")
    suspend fun deletePendaftaran(@Query("id_pendaftaran") pendaftaran: String): Response<Void>
}