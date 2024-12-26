package com.naji.translation.api.libre_translate

import com.naji.translation.api.Resource
import com.naji.translation.model.Translation
import com.naji.translation.model.api.ListOfLanguages
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LibreTranslateRepository {

    private val api: LibreTranslateAPI by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        Retrofit.Builder()
            .client(client)
            .baseUrl("https://translate.argosopentech.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibreTranslateAPI::class.java)
    }

    suspend fun getListOfLanguages(): ListOfLanguages {
        val response = api.getListOfLanguages()
        if(response.isSuccessful && response.body() != null)
            return response.body()!!
        return ListOfLanguages()
    }

    suspend fun translate(
        q: String,
        source: String,
        target: String
    ): Resource<Translation> {
        val response = api.translate(q, source, target)
        if(response.isSuccessful && response.body() != null) {
            return Resource.Success(response.body()!!)
        }
        return Resource.Error(response.message())
    }
}