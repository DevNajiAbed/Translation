package com.naji.translation.api.libre_translate

import com.naji.translation.model.Translation
import com.naji.translation.model.api.ListOfLanguages
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface LibreTranslateAPI {

    @GET("/languages")
    suspend fun getListOfLanguages(): Response<ListOfLanguages>

    @POST("/translate")
    suspend fun translate(
        @Query("q") q: String,
        @Query("source") source: String,
        @Query("target") target: String
    ) : Response<Translation>
}