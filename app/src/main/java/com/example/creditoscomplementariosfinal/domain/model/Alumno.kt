package com.example.creditoscomplementariosfinal.domain.model

data class AlumnoResponse(
    val id: Int,
    val numeroControl: String,
    val nombre: String,
    val apellido: String,
    val correoElectronico: String,
    val fechaRegistro: String,
    val semestre: Int,
    val totalCreditos: Int,
    val carreraNombre: String,
    val carreraId: Int
)

data class EditAlumnoRequest(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val semestre: Int,
    val totalCreditos: Int,
    val carreraId: Int,
    val correoElectronico: String,
    val currentPassword: String? = null,
    val newPassword: String? = null
)


