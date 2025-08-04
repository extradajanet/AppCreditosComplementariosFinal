package com.example.creditoscomplementariosfinal.screens.register

import androidx.lifecycle.viewModelScope
import com.example.creditoscomplementariosfinal.data.remote.RetrofitInstance
import com.example.creditoscomplementariosfinal.domain.model.RegisterRequest
import com.example.creditoscomplementariosfinal.ui.components.ErrorViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class RegisterViewModel : ErrorViewModel() {

    var email = MutableStateFlow("")
    var password = MutableStateFlow("")
    var confirmPassword = MutableStateFlow("")
    var numeroControl = MutableStateFlow("")
    var nombre = MutableStateFlow("")
    var apellido = MutableStateFlow("")
    val semestreInput = MutableStateFlow("")
    var totalCreditos = MutableStateFlow(0)
    var carreraId = MutableStateFlow(0)

    fun register(onRegisterSuccess: () -> Unit){
        val semestreInt = semestreInput.value.toIntOrNull() ?: 0
        viewModelScope.launch {
            dismissErrorDialog()

            try{
                val response = RetrofitInstance.api.register(
                    RegisterRequest(
                        email = email.value,
                        password = password.value,
                        confirmPassword = confirmPassword.value,
                        numeroControl = numeroControl.value,
                        nombre = nombre.value,
                        apellido = apellido.value,
                        semestre = semestreInt,
                        totalCreditos = totalCreditos.value,
                        carreraId = carreraId.value)
                )
                if(response.isSuccessful){
                    //send to login screen
                    onRegisterSuccess()
                }else{
                    val errorMessage = "El usuario ya fue creado"
                    showErrorDialog(errorMessage)
                }
            }catch (e: Exception){
                val errorMessage = "Error de red: ${e.message ?: "Verifica tu conexión."}"
                showErrorDialog(errorMessage)
                }
            }
        }
    }
