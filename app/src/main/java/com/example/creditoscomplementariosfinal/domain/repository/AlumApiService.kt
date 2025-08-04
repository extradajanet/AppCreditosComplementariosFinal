package com.example.creditoscomplementariosfinal.domain.repository
import com.example.creditoscomplementariosfinal.domain.model.AlumnoResponse
import com.example.creditoscomplementariosfinal.domain.model.EditAlumnoRequest

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface AlumApiService {
    @GET("/api/alumno/{id}")
    suspend fun getAlumnoById(@Path("id") id: Int): Response<AlumnoResponse>

    @PUT("/api/alumno/{id}")
    suspend fun editAlumno(@Path("id") id: Int,@Body editRequest: EditAlumnoRequest): Response<AlumnoResponse>
}