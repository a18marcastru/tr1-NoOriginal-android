package com.example.tr1.network

import com.example.tr1.model.ProductesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

object RetrofitInstance {


}

interface TakeAwayApi {
    @GET("ruta/a/tu/endpoint") // Cambia esto a la ruta de tu endpoint
    fun getProducts(): Call<ProductesResponse>
}