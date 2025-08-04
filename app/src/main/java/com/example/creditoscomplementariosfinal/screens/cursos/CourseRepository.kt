package com.example.creditoscomplementariosfinal.screens.cursos

import com.example.creditoscomplementariosfinal.data.remote.RetrofitInstance
import com.example.creditoscomplementariosfinal.domain.model.CourseResponse

interface CourseRepository {
    suspend fun getCourses(): Result<List<CourseResponse>>
}

class CourseRepositoryImpl : CourseRepository {
    override suspend fun getCourses(): Result<List<CourseResponse>> {
        return try {
            val response = RetrofitInstance.api3.getCourses()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
