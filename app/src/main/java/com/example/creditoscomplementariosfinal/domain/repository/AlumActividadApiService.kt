package com.example.creditoscomplementariosfinal.domain.repository

import com.example.creditoscomplementariosfinal.domain.model.AlumnoActividadRequest
import com.example.creditoscomplementariosfinal.domain.model.AlumnoActividadResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlumActividadApiService {
    @POST("api/AlumnoActividad")
    suspend fun inscribirAlumnoActividad(@Body alumnoActividad: AlumnoActividadRequest): Response<Unit>

    @GET("/api/AlumnoActividad/cursos-alumno/{alumnoId}")
    suspend fun obtenerHistorial(@Path("alumnoId") alumnoId: Int): Response<List<AlumnoActividadResponse>>

    @GET ("/api/AlumnoActividad/{alumnoId}/{actividadId}")
    suspend fun verificarAlumnoActividad(
        @Path("alumnoId") alumnoId: Int,
        @Path("actividadId") actividadId: Int
    ): Response<Unit>

    @DELETE("/api/AlumnoActividad/{alumnoId}/{actividadId}")
    suspend fun eliminarAlumnoActividad(@Path("alumnoId") alumnoId: Int, @Path("actividadId") actividadId: Int): Response<Unit>

}