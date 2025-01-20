package com.example.finalucp_113.service

import com.example.finalucp_113.model.Siswa
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface SiswaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("getbyidsiswa.php/{id_siswa}")
    suspend fun getSiswaByIdSiswa(@Query("id_siswa") idSiswa: Siswa): Siswa

    @POST("insertsiswa.php")
    suspend fun insertSiswa(@Body siswa: Siswa)

    @PUT("editsiswa.php/{id_siswa}")
    suspend fun updateSiswa(@Query("id_siswa") idSiswa: Siswa, @Body siswa: Siswa)

    @DELETE("deletesiswa.php/{id_siswa}")
    suspend fun deleteSiswa(@Query("id_siswa") siswa: Siswa): Response<Void>

}