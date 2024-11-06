package com.example.tr1.model

data class Product(
    val idProducte: Int,
    val nomProducte: String,
    val Descripcio: String,
    val Preu: Double,
    var Stock: Int,
    val Imatge: String,
    var quantity: Int = 1
)

data class CanviStock(
    val idProducte: Int,
    val Stock: Int
)

data class ProductesResponse(
    val productes: List<Product>
)