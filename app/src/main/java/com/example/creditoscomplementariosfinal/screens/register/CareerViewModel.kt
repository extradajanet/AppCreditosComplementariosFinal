package com.example.creditoscomplementariosfinal.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.creditoscomplementariosfinal.data.remote.RetrofitInstance
import com.example.creditoscomplementariosfinal.domain.model.CareerResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CareerViewModel : ViewModel() {

    private val _careers = MutableStateFlow<List<CareerResponse>>(emptyList())
    val careers: StateFlow<List<CareerResponse>> = _careers

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchCareers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getCarreras() // assuming RetrofitInstance.api is AuthApiService
                if (response.isSuccessful) {
                    _careers.value = response.body() ?: emptyList()
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Ocurrió un error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
