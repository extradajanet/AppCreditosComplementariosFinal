package com.example.creditoscomplementariosfinal.screens.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.creditoscomplementariosfinal.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.creditoscomplementariosfinal.ui.components.ErrorModal


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: (Int) -> Unit,
    navController: NavController
) {
    val loginColor1 = colorResource(id = R.color.Login1)
    val loginColor2 = colorResource(id = R.color.Login2)
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.logout()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(loginColor1, loginColor2)))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginHeader()
        Spacer(modifier = Modifier.padding(16.dp))
        LoginBody(
            uiState = uiState,
            onUsernameChange = viewModel::onUsernameChange,
            onPasswordChange = viewModel::onPasswordChange,
            onLoginClick = viewModel::login,
            onDismissError = viewModel::dismissErrorDialog,
            onForgotPassword = viewModel::showForgotPasswordMessage,
            onLoginSuccess = onLoginSuccess
        )
        Spacer(modifier = Modifier.weight(1f))
        LoginFooter(navController = navController)
    }
}


@Composable
fun LoginHeader(){
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Logo",
        modifier = Modifier
            .padding(top = 24.dp)
            .size(300.dp,246.dp)
            .fillMaxWidth()
    )
    Text(
        text = "Bienvenido",
        color = colorResource(id = R.color.white),
        fontFamily = FontFamily(Font(R.font.murecho_bold)),
        fontSize = 55.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun LoginBody(
    uiState: LoginUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onDismissError: () -> Unit,
    onForgotPassword: () -> Unit,
    onLoginSuccess: (Int) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var hasNavigated by rememberSaveable { mutableStateOf(false) }

    val userId = when (val result = uiState.loginState) {
        is LoginResult.Success -> result.data.alumnoId ?: result.data.coordinadorId ?: result.data.departamentoId
        else -> null
    }

    LaunchedEffect(userId) {
        if (userId != null && !hasNavigated) {
            hasNavigated = true
            onLoginSuccess(userId)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = uiState.username,
            onValueChange = onUsernameChange,
            label = { Text("Número de Control", color = Color.Black) },
            singleLine = true,
            colors = TextFieldDefaults.colors(Color.Black),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(8.dp)
        )
        TextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            label = { Text("Contraseña", color = Color.Black) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(icon, contentDescription = null)
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(Color.Black),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "¿Olvidaste tu contraseña?",
            fontFamily = FontFamily(Font(R.font.murecho_semibold)),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(8.dp)
                .clickable(onClick = onForgotPassword),
            color = Color.White
        )
        FilledTonalButton(
            onClick = onLoginClick,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Iniciar Sesión",
                fontFamily = FontFamily(Font(R.font.murecho_bold)),
                color = Color.White
            )
        }

        if (uiState.showErrorDialog && uiState.dialogErrorMessage != null) {
            ErrorModal(
                messageTitle = uiState.dialogTitle,
                showDialog = uiState.showErrorDialog,
                errorMessage = uiState.dialogErrorMessage,
                onDismiss = onDismissError
            )
        }
    }
}

@Composable
fun LoginFooter(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Text(
            text = "¿No tienes cuenta?",
            color = colorResource(id = R.color.white),
            fontFamily = FontFamily(Font(R.font.murecho_semibold)),
            fontSize = 16.sp

        )
        Text(
            text = "Regístrate",
            color = colorResource(id = R.color.white),
            textDecoration = TextDecoration.Underline,
            fontFamily = FontFamily(Font(R.font.murecho_bold)),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(start = 4.dp)
                .clickable { navController.navigate("register") }
        )
    }
}
