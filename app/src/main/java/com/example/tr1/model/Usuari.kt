package com.example.tr1.model

data class Usuari(
    val idUser: Int,
    val Nom: String,
    val Correu: String,
    val Contrasenya: String
)

data class UsuarisResponse(
    val usuaris: List<Usuari>
)

data class LoginRequest(
    val Correu: String,
    val Contrasenya: String
)

data class LoginResponse(
    val idUser: Int,
    val Nom: String,
    val Correu: String,
    val Contrasenya: String,
    val Confirmacio: Boolean

)

data class RegisterRequest(
    val Nom: String,
    val Correu: String,
    val Contrasenya: String,
)

data class RegisterResponse(
    val idUser: Int,
    val Nom: String,
    val Correu: String,
    val Contrasenya: String,
    val Confirmacio: Boolean
)

data class UpdateUserRequest(
    val Nom: String,
    val Correu: String,
    val Contrasenya: String,
)

//data class UpdateUserResponse(
//    val idUser: String,
//    val Nom: String,
//    val Correu: String,
//    val Contrasenya: String,
//)