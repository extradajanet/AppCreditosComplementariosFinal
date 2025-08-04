package com.example.creditoscomplementariosfinal.screens.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.creditoscomplementariosfinal.data.remote.RetrofitInstance
import com.example.creditoscomplementariosfinal.domain.model.AlumnoActividadResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoryViewModel: ViewModel() {
    private val _history = MutableStateFlow<List< AlumnoActividadResponse>>(emptyList())
    val history: StateFlow<List<AlumnoActividadResponse>> = _history
    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    fun fetchHistory(alumnoId: Int) {
        viewModelScope.launch {
            _isLoading.value=true
            try {
                val response = RetrofitInstance.api4.obtenerHistorial(alumnoId)
                if (response.isSuccessful) {
                    _history.value = response.body() ?: emptyList()
                } else {
                    _errorMessage.value = "Error: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage ?: "Error desconocido"}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteHistory(alumnoId: Int, actividadId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api4.eliminarAlumnoActividad(alumnoId, actividadId)
                if (response.isSuccessful) {
                    Log.d("Delete", "Eliminado con éxito. Recargando historial...")
                    fetchHistory(alumnoId) // Aquí recargas desde el backend
                } else {
                    _errorMessage.value = "Error al eliminar: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de red: ${e.localizedMessage ?: "Error desconocido"}"
            }
        }
    }



}