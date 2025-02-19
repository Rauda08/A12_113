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

    @GET("getbyidinstruktur.php/{id_instruktur}")
    suspend fun getInstrukturByIdInstruktur(@Query("id_instruktur") idInstruktur: String): Instruktur

    @POST("insertinstruktur.php")
    suspend fun insertInstruktur(@Body instruktur: Instruktur)

    @PUT("editinstruktur.php/{id_instruktur}")
    suspend fun updateInstruktur(@Query("id_instruktur") idInstruktur: String, @Body instruktur: Instruktur)

    @DELETE("deleteinstruktur.php/{id_instruktur}")
    suspend fun deleteInstruktur(@Query("id_instruktur") idInstruktur: String): Response<Void>
}
