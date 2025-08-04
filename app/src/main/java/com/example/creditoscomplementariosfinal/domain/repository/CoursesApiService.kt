package com.example.creditoscomplementariosfinal.domain.repository

import com.example.creditoscomplementariosfinal.domain.model.CareerResponse
import com.example.creditoscomplementariosfinal.domain.model.CourseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoursesApiService {
    @GET("api/Actividades")
    suspend fun getCourses(): Response<List<CourseResponse>>

    @GET("api/Actividades/{id}")
    suspend fun getCourseById(@Path("id") id: Int): Response<CourseResponse>


}