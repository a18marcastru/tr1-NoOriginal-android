package com.example.tr1.model

data class Product(
    val idProducte: String,
    val nomProducte: String,
    val Descripcio: String,
    val Preu: Double,
    val Stock: Int,
    val Imatge: String
)

data class ProductesResponse(
    val productes: List<Product>
)