package com.example.creditoscomplementariosfinal.screens.coursedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.creditoscomplementariosfinal.data.remote.RetrofitInstance
import com.example.creditoscomplementariosfinal.domain.model.AlumnoActividadRequest
import com.example.creditoscomplementariosfinal.domain.model.CourseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CDetailViewModel : ViewModel() {
    private val _course = MutableStateFlow<CourseResponse?>(null)
    val course: StateFlow<CourseResponse?> = _course

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchCourse(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api3.getCourseById(id)
                if (response.isSuccessful) {
                    _course.value = response.body()
                } else {
                    // Handle error
                    _error.value = "Error al obtener curso"
                }
            } catch (e: Exception) {
                _error.value = "Error de red: ${e.message}"
            }
        }
    }

    fun inscribir(onEnrollSuccess: () -> Unit,alumnoId: Int, actividadId: Int) {
        viewModelScope.launch {
            try {
                val request = AlumnoActividadRequest(alumnoId = alumnoId, actividadId = actividadId)
                val VerResponse =
                    RetrofitInstance.api4.verificarAlumnoActividad(alumnoId= alumnoId, actividadId=actividadId)
                if (VerResponse.isSuccessful) {
                    _error.value = "El alumno ya esta inscrito a esta actividad"
                } else {
                    val response = RetrofitInstance.api4.inscribirAlumnoActividad(request)
                    if (response.isSuccessful) {
                        onEnrollSuccess()
                    } else {
                        // Handle error
                        _error.value = "Error al inscribir alumno"
                    }
                }
            } catch (e: Exception) {
                _error.value = "Error de red: ${e.message}"
            }
        }
    }
}