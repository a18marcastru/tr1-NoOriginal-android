package com.example.tr1.network

import com.example.tr1.model.ProductesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.GET

object RetrofitInstance {
    private const val BASE_URL = "http://prejuicengo.dam.inspedralbes.cat:20869/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: TakeAwayApi by lazy {
        retrofit.create(TakeAwayApi::class.java)
    }
}

interface TakeAwayApi {
    @GET("getProductes") // Cambia esto a la ruta de tu endpoint
    fun getProducts(): Call<ProductesResponse>
}