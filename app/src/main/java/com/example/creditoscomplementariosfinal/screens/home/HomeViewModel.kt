package com.example.creditoscomplementariosfinal.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.creditoscomplementariosfinal.data.remote.RetrofitInstance
import com.example.creditoscomplementariosfinal.domain.model.AlumnoResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _alumno = MutableStateFlow<AlumnoResponse?>(null)
    val alumno: StateFlow<AlumnoResponse?> = _alumno

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchAlumno(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api2.getAlumnoById(id)
                if (response.isSuccessful) {
                    _alumno.value = response.body()
                } else {
                    _error.value = "Error al obtener usuario"
                }
            } catch (e: Exception) {
                _error.value = "Error de red: ${e.message}"
            }
        }
    }
}
