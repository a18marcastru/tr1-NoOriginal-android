package com.example.tr1.model

data class Product(
    val idProducte: Int,
    val nomProducte: String,
    val Descripcio: String,
    val Preu: Double,
    val Stock: Int,
    val Imatge: String,
    var quantity: Int = 1
)

data class ProductesResponse(
    val productes: List<Product>
)