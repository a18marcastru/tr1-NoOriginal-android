package com.example.tr1.network


import com.example.tr1.model.ComandesResponse
import com.example.tr1.model.LoginRequest
import com.example.tr1.model.LoginResponse
import com.example.tr1.model.ProductesResponse
import retrofit2.http.Path
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:3010/"

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
    @GET("getProductesBD")
    fun getProducts(): Call<ProductesResponse>

    @GET("getHistorialComandes/{id}")
    fun getComandes(@Path("id") idUsuari: String): Call<ComandesResponse>

    @POST("loginBD")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}