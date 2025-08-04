package com.example.creditoscomplementariosfinal.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.creditoscomplementariosfinal.R
import com.example.creditoscomplementariosfinal.domain.model.CareerResponse
import androidx.compose.runtime.collectAsState


@Composable
fun RegisterScreen(viewModel: RegisterViewModel= viewModel(),careerViewModel: CareerViewModel = viewModel(),RViewModel: RegisterViewModel = viewModel(),onRegisterSuccess: () -> Unit ){
    val loginColor1 = colorResource(id = R.color.Login1)
    val loginColor2 = colorResource(id = R.color.Login2)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(colors = listOf(loginColor1, loginColor2))
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        RegisterHeader()
        RegisterBody(registerViewModel = RViewModel,careerViewModel = careerViewModel,onRegisterSuccess = onRegisterSuccess)

    }
}

@Composable
fun RegisterHeader(){
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Logo",
        modifier = Modifier
            .padding(top = 24.dp)
            .size(200.dp,146.dp)
            .fillMaxWidth()
    )
    Text(
        text = "¡Regístrate!",
        color = colorResource(id = R.color.white),
        fontFamily = FontFamily(Font(R.font.murecho_bold)),
        fontSize = 35.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(12.dp)
    )
}

@Composable
fun RegisterBody(
    registerViewModel: RegisterViewModel,
    careerViewModel: CareerViewModel,
    onRegisterSuccess: () -> Unit
) {
    val scrollState = rememberScrollState()
    val careers by careerViewModel.careers.collectAsState()
    val nombre by registerViewModel.nombre.collectAsState()
    val apellido by registerViewModel.apellido.collectAsState()
    val numeroControl by registerViewModel.numeroControl.collectAsState()
    val email by registerViewModel.email.collectAsState()
    val password by registerViewModel.password.collectAsState()
    val confirmPassword by registerViewModel.confirmPassword.collectAsState()
    val semestre by registerViewModel.semestreInput.collectAsState()
    val carreraId by registerViewModel.carreraId.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        careerViewModel.fetchCareers()
    }

    Column(
        modifier = Modifier.verticalScroll(scrollState).padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = nombre,
            onValueChange = { registerViewModel.nombre.value = it },
            label = { Text("Nombre") },
            singleLine = true,
            colors = TextFieldDefaults.colors(Color.Black),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(8.dp)
        )
        TextField(
            value = apellido,
            onValueChange = { registerViewModel.apellido.value = it },
            label = { Text("Apellidos") },
            singleLine = true,
            colors = TextFieldDefaults.colors(Color.Black),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(8.dp)
        )
        TextField(
            value = numeroControl,
            onValueChange = { registerViewModel.numeroControl.value = it },
            label = { Text("Numero de Control") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = TextFieldDefaults.colors(Color.Black),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(8.dp)
        )
        TextField(
            value = semestre,
            onValueChange = {
                if (it.all { char -> char.isDigit() } && it.length <= 2) {
                    registerViewModel.semestreInput.value = it
                }
            },
            label = { Text("Semestre") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = TextFieldDefaults.colors(Color.Black),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(8.dp)
        )
        CareerDropdown(
            careers = careers,
            selectedValue = carreraId.takeIf { it != 0 },
            onValueSelected = { registerViewModel.carreraId.value = it }
        )
        TextField(
            value = email,
            onValueChange = { registerViewModel.email.value = it },
            label = { Text("Correo Electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = TextFieldDefaults.colors(Color.Black),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(8.dp)
        )
        TextField(
            value = password,
            onValueChange = { registerViewModel.password.value = it },
            label = { Text("Contraseña") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            colors = TextFieldDefaults.colors(Color.Black),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(8.dp)
        )
        TextField(
            value = confirmPassword,
            onValueChange = { registerViewModel.confirmPassword.value = it },
            label = { Text("Confirmar Contraseña") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            colors = TextFieldDefaults.colors(Color.Black),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(8.dp)
        )
        FilledTonalButton(
            onClick = { registerViewModel.register(onRegisterSuccess) },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Crear Cuenta",
                fontFamily = FontFamily(Font(R.font.murecho_bold)),
                color = Color.White
            )
        }
    }
}



@Composable
fun CareerDropdown(
    careers: List<CareerResponse>,
    selectedValue: Int?,
    onValueSelected: (Int) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    val selectedText = careers.find { it.id == selectedValue }?.nombre ?: ""

    Box() {
        TextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text("Carrera") },
            colors = TextFieldDefaults.colors(Color.Black),
            shape= RoundedCornerShape(16.dp),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            modifier = Modifier.padding(8.dp)
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.padding(start = 16.dp).align(Alignment.TopStart)
        ) {
            careers.forEach { career ->
                DropdownMenuItem(
                    text = { Text(career.nombre) },
                    onClick = {
                        onValueSelected(career.id)
                        expanded = false
                    }
                )
            }
        }
    }
}


