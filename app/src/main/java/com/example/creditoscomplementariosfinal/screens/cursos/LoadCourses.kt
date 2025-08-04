package com.example.creditoscomplementariosfinal.screens.cursos

import com.example.creditoscomplementariosfinal.domain.model.CourseResponse

suspend fun loadCourses(repository: CourseRepository): Result<List<CourseResponse>> {
    return repository.getCourses()
}
