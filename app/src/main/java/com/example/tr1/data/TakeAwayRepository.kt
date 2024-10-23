package com.example.tr1.data

import android.content.Context
import com.example.tr1.R
import com.example.tr1.model.Product
import com.example.tr1.model.ProductesResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun loadProductsFromJson(context: Context): List<Product>? {
    // Cargar el archivo JSON desde la carpeta raw
    val jsonString = context.resources.openRawResource(R.raw.products) // Asegúrate de que el nombre del archivo sea correcto
        .bufferedReader().use { it.readText() }

    // Crear una instancia de Gson
    val gson = Gson()

    // Especificar el tipo de respuesta esperado
    val productType = object : TypeToken<ProductesResponse>() {}.type

    // Deserializar el JSON a un objeto ProductesResponse
    val response: ProductesResponse = gson.fromJson(jsonString, productType)

    // Retornar la lista de productos
    return response.productes
}