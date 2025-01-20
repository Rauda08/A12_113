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
    @GET("getPendaftaran.php")
    suspend fun getPendaftaran(): List<Pendaftaran>
    @GET("getbyidPendaftaran.php/{id_pendaftaran}")
    suspend fun getPendaftaranByIdPendaftaran(@Query("id_pendaftaran") idPendaftaran: Int): Pendaftaran

    @POST("insertPendaftaran.php")
    suspend fun insertPendaftaran(@Body pendaftaran: Pendaftaran)

    @PUT("editPendaftaran.php/{id_pendaftaran}")
    suspend fun updatePendaftaran(@Query("id_pendaftaran") idPendaftaran: Int, @Body pendaftaran: Pendaftaran)

    @DELETE("deletePendaftaran.php/{id_pendaftaran}")
    suspend fun deletePendaftaran(@Query("id_pendaftaran") pendaftaran: Int): Response<Void>
}