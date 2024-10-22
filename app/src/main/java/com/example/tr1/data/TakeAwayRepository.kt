package com.example.tr1.data

import android.content.Context
import com.example.tr1.R
import com.example.tr1.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.tr1.model.ProductesResponse

fun loadProductsFromJson(context: Context): List<Product>? {
    val jsonString = context.resources.openRawResource(R.raw.products) // Asegúrate de que el nombre del archivo sea correcto
        .bufferedReader().use { it.readText() }

    val gson = Gson()
    val productType = object : TypeToken<ProductesResponse>() {}.type
    val response: ProductesResponse = gson.fromJson(jsonString, productType)

    return response.productes
}