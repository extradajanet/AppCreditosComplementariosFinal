package com.example.creditoscomplementariosfinal.domain.model

data class AlumnoActividadRequest(
    val alumnoId: Int,
    val actividadId: Int,
    val estadoAlumnoActividad: Int? = null, // Optional, omit when sending
    val fechaInscripcion: String? = null,   // Optional, omit when sending
    val genero: Int? = null
)

data class AlumnoActividadResponse(
    val actividadId: Int,
    val nombre: String,
    val descripcion: String,
    val imagenNombre: String,
    val creditos: Int,
    val fechaInicio: String,
    val fechaFin: String,
    val estadoAlumnoActividad: Int,
    val estadoActividad: Int,
    val genero: Int? = null
)