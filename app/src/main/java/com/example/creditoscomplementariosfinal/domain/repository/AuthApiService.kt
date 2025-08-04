package com.example.creditoscomplementariosfinal.domain.repository

// ApiService.kt
import com.example.creditoscomplementariosfinal.domain.model.CareerResponse
import com.example.creditoscomplementariosfinal.domain.model.LoginRequest
import com.example.creditoscomplementariosfinal.domain.model.LoginResponse
import com.example.creditoscomplementariosfinal.domain.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.GET

interface AuthApiService {
    @POST("api/Auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/Auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>

    @GET("api/Carrera/carreras")
    suspend fun getCarreras(): Response<List<CareerResponse>>
}

