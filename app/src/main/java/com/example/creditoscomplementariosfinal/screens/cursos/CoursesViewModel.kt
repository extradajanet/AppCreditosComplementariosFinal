package com.example.creditoscomplementariosfinal.screens.cursos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.creditoscomplementariosfinal.data.remote.RetrofitInstance
import com.example.creditoscomplementariosfinal.domain.model.CourseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CoursesViewModel : ViewModel() {
    private val _courses = MutableStateFlow<List<CourseResponse>>(emptyList())
    val courses: MutableStateFlow<List<CourseResponse>> = _courses
    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    fun fetchCourses() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api3.getCourses()
                if (response.isSuccessful) {
                    _courses.value = response.body() ?: emptyList()
                } else {
                    // Handle error
                }
            }catch (e: Exception) {
                _errorMessage.value = "Ocurrió un error: ${e.localizedMessage}"
            }finally {
                _isLoading.value = false
            }
        }
    }
}