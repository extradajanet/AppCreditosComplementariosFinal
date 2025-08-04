package com.example.creditoscomplementariosfinal.domain.model

// LoginRequest
data class LoginRequest(
    val usuario: String,
    val password: String
)

// LoginResponse
data class LoginResponse(
    val token: String,
    val expiration: String,
    val alumnoId: Int?,
    val departamentoId: Int?,
    val coordinadorId: Int?
)

//RegisterRequest
data class RegisterRequest(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val numeroControl: String,
    val nombre: String,
    val apellido: String,
    val semestre: Int,
    val totalCreditos: Int,
    val carreraId: Int
)