package com.example.creditoscomplementariosfinal.screens.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.creditoscomplementariosfinal.data.remote.RetrofitInstance
import com.example.creditoscomplementariosfinal.domain.model.LoginRequest
import com.example.creditoscomplementariosfinal.domain.model.LoginResponse
import com.example.creditoscomplementariosfinal.ui.components.ErrorViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val loginState: LoginResult = LoginResult.Idle,
    val showErrorDialog: Boolean = false,
    val dialogErrorMessage: String? = null,
    val dialogTitle: String = "",
)

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun showForgotPasswordMessage() {
        _uiState.update {
            it.copy(
                showErrorDialog = true,
                dialogErrorMessage = "Por favor, comunícate con el Departamento de Conectividad en el Centro de Cómputo para restablecer tu contraseña.",
                dialogTitle = "¿Olvidaste tu contraseña?"
            )
        }
    }
    fun onUsernameChange(value: String) {
        _uiState.update { it.copy(username = value) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
    }
    fun resetLoginState() {
        _uiState.update {
            it.copy(
                loginState = LoginResult.Idle,
                showErrorDialog = false,
                dialogErrorMessage = null,
                dialogTitle = ""
            )
        }
    }

    fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(loginState = LoginResult.Idle, showErrorDialog = false) }
            try {
                val response = RetrofitInstance.api.login(
                    LoginRequest(usuario = uiState.value.username, password = uiState.value.password)
                )
                if (response.isSuccessful) {
                    _uiState.update { it.copy(loginState = LoginResult.Success(response.body()!!)) }
                } else {
                    showError("Credenciales incorrectas", "Error")
                }
            } catch (e: Exception) {
                showError("Error de red: ${e.message ?: "Verifica tu conexión."}", "Error")
            }
        }
    }

    private fun showError(message: String, title: String) {
        _uiState.update {
            it.copy(
                loginState = LoginResult.Error(message),
                showErrorDialog = true,
                dialogErrorMessage = message,
                dialogTitle = title
            )
        }
    }

    fun dismissErrorDialog() {
        _uiState.update {
            it.copy(
                showErrorDialog = false,
                dialogErrorMessage = null,
                dialogTitle = ""
            )
        }
    }


    fun logout() {
        _uiState.value = LoginUiState()
    }
}

sealed class LoginResult {
    object Idle : LoginResult()
    data class Success(val data: LoginResponse) : LoginResult()
    data class Error(val message: String) : LoginResult()
}
