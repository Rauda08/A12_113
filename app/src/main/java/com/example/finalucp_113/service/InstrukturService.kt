package com.example.finalucp_113.service

import com.example.finalucp_113.model.Instruktur
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface InstrukturService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("getinstruktur.php")
    suspend fun getInstruktur(): List<Instruktur>

    @GET("getbyidInstruktur.php/{id_instruktur}")
    suspend fun getInstrukturByIdInstruktur(@Query("id_instruktur") idInstruktur: Int): Instruktur

    @POST("insertInstruktur.php")
    suspend fun insertInstruktur(@Body instruktur: Instruktur)

    @PUT("editInstruktur.php/{id_instruktur}")
    suspend fun updateInstruktur(@Query("id_instruktur") idInstruktur: Int, @Body instruktur: Instruktur)

    @DELETE("deleteInstruktur.php/{id_instruktur}")
    suspend fun deleteInstruktur(@Query("id_instruktur") idInstruktur: Int): Response<Void>
}
