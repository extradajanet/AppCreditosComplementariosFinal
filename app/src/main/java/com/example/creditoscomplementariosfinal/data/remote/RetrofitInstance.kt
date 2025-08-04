package com.example.creditoscomplementariosfinal.data.remote

import com.example.creditoscomplementariosfinal.domain.repository.AlumActividadApiService
import com.example.creditoscomplementariosfinal.domain.repository.AlumApiService
import com.example.creditoscomplementariosfinal.domain.repository.AuthApiService
import com.example.creditoscomplementariosfinal.domain.repository.CoursesApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:5091/"

    val api: AuthApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiService::class.java)
    }
    val api2: AlumApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlumApiService::class.java)
    }
    val api3: CoursesApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoursesApiService::class.java)
    }
    val api4: AlumActividadApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlumActividadApiService::class.java)
    }

}
