package com.example.creditoscomplementariosfinal.screens.perfil

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.creditoscomplementariosfinal.data.remote.RetrofitInstance
import com.example.creditoscomplementariosfinal.domain.model.AlumnoResponse
import com.example.creditoscomplementariosfinal.domain.model.EditAlumnoRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {
    var alumno by mutableStateOf<AlumnoResponse?>(null)
        private set
    var error by mutableStateOf<String?>(null)
        private set
    var currentPassword by mutableStateOf("")
        private set

    var newPassword by mutableStateOf("")
        private set

    fun onCurrentPasswordChange(value: String) {
        currentPassword = value
    }

    fun onNewPasswordChange(value: String) {
        newPassword = value
    }

    var isEditing by mutableStateOf(false)
        private set

    fun fetchAlumno(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api2.getAlumnoById(id)
                if (response.isSuccessful) {
                    alumno = response.body()
                } else {
                    error = "Error al obtener usuario"
                }
            } catch (e: Exception) {
                error = "Error de red: ${e.message}"
            }
        }
    }

    fun toggleEdit() {
        isEditing = !isEditing
    }

    fun updateField(field: String, value: String) {
        Log.d("PerfilDebug", "Actualizando campo $field con $value")
        alumno?.let { current ->
            alumno = when (field) {
                "nombre" -> current.copy(nombre = value)
                "apellido" -> current.copy(apellido = value)
                "correoElectronico" -> current.copy(correoElectronico = value)
                else -> current
            }
        }
    }


    fun saveChanges() {
        viewModelScope.launch {
            alumno?.let { alumno ->
                val request = EditAlumnoRequest(
                    id = alumno.id,
                    nombre = alumno.nombre ?: "",
                    apellido = alumno.apellido ?: "",
                    semestre = alumno.semestre ?: 0,
                    totalCreditos = alumno.totalCreditos ?: 0,
                    carreraId = alumno.carreraId ?: 0,
                    correoElectronico = alumno.correoElectronico ?: "",
                    currentPassword = currentPassword.takeIf { it.isNotBlank() },
                    newPassword = newPassword.takeIf { it.isNotBlank() }
                )

                Log.d("PerfilDebug", "Antes del try")

                try {
                    Log.d("PerfilDebug", "Entra al try")

                    val response = RetrofitInstance.api2.editAlumno(alumno.id, request)

                    if (response.isSuccessful) {
                        Log.d("PerfilDebug", "Cambios guardados con éxito: ${response.body()}")
                        fetchAlumno(alumno.id)
                        isEditing = false
                        currentPassword = ""
                        newPassword = ""
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("PerfilDebug", "Error al guardar cambios: $errorBody")
                        error = "Error al guardar cambios: $errorBody"
                    }
                } catch (e: Exception) {
                    Log.e("PerfilDebug", "Excepción al guardar cambios", e)
                    error = "Error de red al guardar: ${e.message}"
                }
            }
        }
    }

}


