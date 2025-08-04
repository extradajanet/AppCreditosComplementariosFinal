package com.example.creditoscomplementariosfinal.domain.model

data class CourseResponse(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val fechaInicio: String,
    val fechaFin: String,
    val creditos: Int,
    val capacidad: Int,
    val dias: Int,
    val horaInicio: String,
    val horaFin: String,
    val tipoActividad: Int,
    val estadoActividad: Int,
    val imagenNombre: String,
    val departamentoId: Int,
    val departamentoNombre: String,
    val carreraNombres: List<String>,
    val genero: Int
)
