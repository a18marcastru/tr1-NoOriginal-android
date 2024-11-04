package com.example.tr1.data

import android.content.Context
import com.example.tr1.model.ProductesResponse
import android.util.Log
import com.example.tr1.R
import com.example.tr1.model.ComandesResponse
import com.example.tr1.model.LoginRequest
import com.example.tr1.model.LoginResponse
import com.example.tr1.model.Usuari
import com.example.tr1.model.UsuarisResponse
import com.example.tr1.network.RetrofitInstance
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun loadProductsFromApi(onProductsLoaded: (ProductesResponse?) -> Unit) {
    val call = RetrofitInstance.api.getProducts()

    call.enqueue(object : Callback<ProductesResponse> {
        override fun onResponse(call: Call<ProductesResponse>, response: Response<ProductesResponse>) {
            if (response.isSuccessful) {
                // Devolvemos los productos cuando la respuesta es exitosa
                onProductsLoaded(response.body())
            } else {
                // Logueamos el error en la respuesta y devolvemos null
                Log.e("TakeAwayApp", "Error en la respuesta: ${response.code()}")
                onProductsLoaded(null)
            }
        }

        override fun onFailure(call: Call<ProductesResponse>, t: Throwable) {
            // Logueamos el error en caso de fallo de conexión y devolvemos null
            Log.e("TakeAwayApp", "Error de conexión: ${t.message}")
            onProductsLoaded(null)
        }
    })
}

fun loadComandesFromApi(idUsuari: String, onComandesLoaded: (ComandesResponse?) -> Unit) {
    val call = RetrofitInstance.api.getComandes(idUsuari)
    Log.d("comandes", "ID d'usuari: $idUsuari")
    call.enqueue(object : Callback<ComandesResponse> {
        override fun onResponse(call: Call<ComandesResponse>, response: Response<ComandesResponse>) {
            if (response.isSuccessful) {
                onComandesLoaded(response.body())
                Log.d("comandes", "Respuesta exitosa: ${response.body()}")
            } else {
                Log.e("comandes", "Error en la respuesta: ${response.code()}")
                onComandesLoaded(null)
            }
        }

        override fun onFailure(call: Call<ComandesResponse>, t: Throwable) {
            Log.e("TakeAwayApp", "Error de conexión: ${t.message}")
            onComandesLoaded(null)
        }
    })
}

fun login(loginRequest: LoginRequest, onLoginResult: (LoginResponse?, Throwable?) -> Unit) {
    val call = RetrofitInstance.api.login(loginRequest)

    call.enqueue(object : Callback<LoginResponse> {
        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
                onLoginResult(response.body(), null)
            } else {
                Log.e("TakeAwayApp", "Login error: ${response.code()}")
                onLoginResult(null, null) // or create a custom exception
            }
        }

        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            Log.e("TakeAwayApp", "Login failed: ${t.message}")
            onLoginResult(null, t)
        }
    })
}

fun loadUsuarisFromJson(context: Context): List<Usuari> {
    val jsonString = context.resources.openRawResource(R.raw.users)
        .bufferedReader().use { it.readText() }

    val gson = Gson()
    val usuariType = object : TypeToken<UsuarisResponse>() {}.type
    val response: UsuarisResponse = gson.fromJson(jsonString, usuariType)

    return response.usuaris
}