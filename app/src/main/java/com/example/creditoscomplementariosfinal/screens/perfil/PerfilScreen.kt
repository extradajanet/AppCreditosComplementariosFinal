package com.example.creditoscomplementariosfinal.screens.perfil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.creditoscomplementariosfinal.R
import com.example.creditoscomplementariosfinal.domain.model.AlumnoResponse
import com.example.creditoscomplementariosfinal.ui.components.ScreenFooter
import com.example.creditoscomplementariosfinal.ui.components.ScreenHeader

@Composable
fun PerfilScreen(
    userId: Int,
    navController: NavController,
    viewModel: PerfilViewModel = viewModel()
) {
    val loginColor1 = colorResource(id = R.color.Login1)
    val loginColor2 = colorResource(id = R.color.Login2)

    val alumno = viewModel.alumno
    val isEditing = viewModel.isEditing

    LaunchedEffect(userId) {
        viewModel.fetchAlumno(userId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(colors = listOf(loginColor1, loginColor2))
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenHeader(
                title = "Perfil",
                showBackButton = true,
                onBackClick = { navController.navigate("home/$userId") }
            )

            alumno?.let {
                PerfilInfo(
                    alumno = it,
                    isEditing = isEditing,
                    onEditClick = { viewModel.toggleEdit() },
                    onFieldChange = { field, value -> viewModel.updateField(field, value) },
                    onSaveClick = { viewModel.saveChanges() },
                    currentPassword = viewModel.currentPassword,
                    newPassword = viewModel.newPassword,
                    onCurrentPasswordChange = viewModel::onCurrentPasswordChange,
                    onNewPasswordChange = viewModel::onNewPasswordChange
                )

            } ?: Text("Cargando...")

            Spacer(modifier = Modifier.weight(1f))

            ScreenFooter(
                onHomeClick = { navController.navigate("home/$userId") },
                onHistoryClick = { navController.navigate("historial/$userId") },
                onProfileClick = { navController.navigate("perfil/$userId") }
            )
        }
    }
}



@Composable
fun PerfilInfo(
    alumno: AlumnoResponse,
    isEditing: Boolean,
    onEditClick: () -> Unit,
    onFieldChange: (field: String, value: String) -> Unit,
    onSaveClick: () -> Unit,
    currentPassword: String,
    newPassword: String,
    onCurrentPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit
)
{
    Column {
        Image(
            painter = painterResource(id = R.drawable.iconuser),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        IconButton(onClick = {
            if (isEditing) onSaveClick() else onEditClick()
        }, modifier = Modifier.align(Alignment.End)) {
            Icon(
                imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                contentDescription = if (isEditing) "Guardar" else "Editar Perfil",
                tint = Color.White
            )
        }

        PerfilField(
            label = "Nombre",
            value = alumno.nombre ?: "",
            isEditing = isEditing,
            onValueChange = { onFieldChange("nombre", it) }
        )
        PerfilField(
            label = "Apellido",
            value = alumno.apellido ?: "",
            isEditing = isEditing,
            onValueChange = { onFieldChange("apellido", it) }
        )
        PerfilField(
            label = "Correo electrónico",
            value = alumno.correoElectronico ?: "",
            isEditing = isEditing,
            onValueChange = { onFieldChange("correoElectronico", it) }
        )

        // Password fields only in edit mode
        if (isEditing) {
            PerfilField(
                label = "Contraseña actual",
                value = currentPassword,
                isEditing = true,
                onValueChange = onCurrentPasswordChange,
                isPassword = true
            )

            PerfilField(
                label = "Nueva contraseña",
                value = newPassword,
                isEditing = true,
                onValueChange = onNewPasswordChange,
                isPassword = true
            )
        }
    }
}
