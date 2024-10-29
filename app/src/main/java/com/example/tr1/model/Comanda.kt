package com.example.tr1.model

import com.example.tr1.model.Product

data class Comanda(
    val idComanda: Int,
    val idUsuari: Int,
    val productes: List<Product>,
    val preuTotal: Double,
    val estat: EstatComanda,
    val data: String
)

data class ComandesResponse(
    val comandes: List<Comanda>
)

enum class EstatComanda {
    PENDENT_DE_PREPARACIO,
    EN_PREPARACIO,
    PREPARAT_PER_RECOLLIR,
    RECOLLIT
}