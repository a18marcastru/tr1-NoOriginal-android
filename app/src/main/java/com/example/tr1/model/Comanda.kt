package com.example.tr1.model

data class Comanda(
    val idComanda: Int,
    val idUsuari: Int,
    val Productes: List<ProductePerComanda>,
    val PreuTotal: Double,
    val Estat: EstatComanda,
    val Data: String
)

data class ComandesResponse(
    val Comandes: List<Comanda>
)

data class ProductePerComanda(
    val idProducte: Int,
    val nomProducte: String,
    val quantitat: Int,
    val preuTotalProducte: Double
)

enum class EstatComanda {
    PENDENT_DE_PREPARACIO,
    EN_PREPARACIO,
    PREPARAT_PER_RECOLLIR,
    RECOLLIT
}