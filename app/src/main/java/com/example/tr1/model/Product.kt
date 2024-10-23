package com.example.tr1.model

import com.google.gson.annotations.SerializedName

data class Product(
    val nomProducte: String,
    val Descripcio: String,
    val Preu: Double,
    val Stock: Int,
    val Imatge: String
)

data class ProductesResponse(
    val productes: List<Product>
)