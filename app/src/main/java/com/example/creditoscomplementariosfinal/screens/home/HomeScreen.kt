package com.example.creditoscomplementariosfinal.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.creditoscomplementariosfinal.R
import com.example.creditoscomplementariosfinal.screens.login.LoginViewModel


@Composable
fun HomeScreen(userId: Int,viewModel: HomeViewModel = viewModel(),loginViewModel: LoginViewModel = viewModel(),navController: NavController) {
    val loginColor1 = colorResource(id = R.color.Login1)
    val loginColor2 = colorResource(id = R.color.Login2)
    val alumno by viewModel.alumno.collectAsState()
    val error by viewModel.error.collectAsState()
    LaunchedEffect(userId) {
        viewModel.fetchAlumno(userId)
    }
    LaunchedEffect(Unit) {
        loginViewModel.resetLoginState()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(colors = listOf(loginColor1, loginColor2))
            )
            ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            alumno != null -> {
                WelcomeUser(
                    name = "${alumno!!.nombre} ${alumno!!.apellido}",
                    creditText = "${alumno!!.totalCreditos}/5"
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Options(navController = navController,userId,loginViewModel = loginViewModel)
            }
            error != null -> {
                Text(error!!, color = Color.Red, modifier = Modifier.padding(16.dp))
            }
            else -> {
                CircularProgressIndicator(modifier = Modifier.padding(32.dp))
            }
        }

    }
}

@Composable
fun WelcomeUser(
    name: String,
    creditText: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.bienvenido, name),
            lineHeight = 35.sp,
            color = colorResource(id = R.color.white),
            fontFamily = FontFamily(Font(R.font.murecho_bold)),
            fontSize = 35.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 8.dp)
        )

        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(end = 16.dp)
                .wrapContentSize(),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white))
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = creditText,
                    color = colorResource(id = R.color.black),
                    fontFamily = FontFamily(Font(R.font.murecho_bold)),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.cr_ditos),
                    color = colorResource(id = R.color.black),
                    fontFamily = FontFamily(Font(R.font.murecho_bold)),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun Options(navController: NavController,userId: Int,loginViewModel: LoginViewModel) {
    Box(modifier = Modifier.fillMaxSize()){
    Surface(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f)
            .align(Alignment.BottomCenter),
        color = colorResource(id = R.color.LoPlaceholder),
        shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = colorResource(R.color.Login1)),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable {
                            navController.navigate("courses/$userId")
                        }
                ) {
                    Column (
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.graduation),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .size(100.dp, 100.dp)
                        )
                        Text(
                            text = stringResource(R.string.cursos_disponibles),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp),
                            color = Color.White
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
            Card(
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.Login1)),
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(0.5f)
                    .height(200.dp)
                    .clickable {
                        navController.navigate("historial/$userId")
                    }
            ) {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Image(
                        painter = painterResource(id = R.drawable.history__2_),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .size(100.dp, 100.dp)
                    )
                    Text(
                        text = stringResource(R.string.historial),
                        modifier = Modifier.padding(16.dp),
                        color = Color.White
                    )
                }
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.Login1)),
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        navController.navigate("perfil/$userId")
                    }
            ) {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Image(
                        painter = painterResource(id = R.drawable.user__1_),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .size(100.dp, 100.dp)
                    )
                    Text(
                        text = stringResource(R.string.perfil),
                        modifier = Modifier.padding(16.dp),
                        color = Color.White
                    )
                }
            }
        }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = colorResource(R.color.Login1)),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable {
                            loginViewModel.logout()
                            navController.navigate("login") {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logout), // Usa un ícono de logout que tengas
                            contentDescription = stringResource(R.string.cerrar_sesi_n),
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .size(30.dp)
                        )
                        Text(
                            text = stringResource(R.string.cerrar_sesi_n),
                            modifier = Modifier.padding(8.dp),
                            color = Color.White
                        )
                    }
                }
            }

        }
    }
}}
