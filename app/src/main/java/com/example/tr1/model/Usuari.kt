package com.example.tr1.model

// Esto es del user.json, eliminar mas tarde

data class Usuari(
    val idNom: String,
    val Nom: String,
    val Correu: String,
    val Contrasenya: String
)

data class UsuarisResponse(
    val usuaris: List<Usuari>
)