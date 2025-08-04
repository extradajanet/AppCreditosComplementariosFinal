package com.example.creditoscomplementariosfinal.screens.coursedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.creditoscomplementariosfinal.R
import com.example.creditoscomplementariosfinal.domain.model.CourseResponse
import com.example.creditoscomplementariosfinal.ui.utilities.Dia
import com.example.creditoscomplementariosfinal.ui.components.ErrorModal
import com.example.creditoscomplementariosfinal.ui.components.ScreenFooter
import com.example.creditoscomplementariosfinal.ui.components.ScreenHeader
import com.example.creditoscomplementariosfinal.TipoActividad
import com.example.creditoscomplementariosfinal.ui.utilities.formatDate
import com.example.creditoscomplementariosfinal.ui.utilities.formatTime
import com.example.creditoscomplementariosfinal.ui.utilities.getIconForImageName

@Composable
fun CourseDetailScreen(courseId: Int, userId: Int, CDetailViewModel: CDetailViewModel = viewModel(),navController: NavController,onEnrollSuccess: () -> Unit) {
    val loginColor1 = colorResource(id = R.color.Login1)
    val loginColor2 = colorResource(id = R.color.Login2)
    val course by CDetailViewModel.course.collectAsState()
    val error by CDetailViewModel.error.collectAsState()

    LaunchedEffect(courseId) {
        CDetailViewModel.fetchCourse(courseId)
    }
// Local state to control dialog visibility
    var showErrorDialog by remember { mutableStateOf(false) }

    // Show the dialog if there's an error
    LaunchedEffect(error) {
        showErrorDialog = !error.isNullOrEmpty()
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(colors = listOf(loginColor1, loginColor2))
        )){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(26.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenHeader(
                course?.nombre ?: "Curso no encontrado", showBackButton = true,
                onBackClick = { navController.navigate("courses/$userId") })
            when {
                course != null -> CourseInfo(course = course, userId = userId,onEnrollSuccess= onEnrollSuccess)
                error != null -> Text(text = error ?: "Error desconocido", color = Color.Red)
                else -> CircularProgressIndicator()
            }
            ScreenFooter(
                onHomeClick = { navController.navigate("home/$userId") },
                onHistoryClick = { navController.navigate("historial/$userId") },
                onProfileClick = { navController.navigate("perfil/$userId")  }
            )
        }

    }
    ErrorModal(
        showDialog = showErrorDialog,
        messageTitle = "Error",
        errorMessage = error ?: "",
        onDismiss = {
            showErrorDialog = false
            // You can add this method to clear error state
        }
    )
}

@Composable
fun CourseInfo(course: CourseResponse?, userId: Int, CDetailViewModel: CDetailViewModel = viewModel(),onEnrollSuccess: () -> Unit) {
    if (course == null) {
        Text("Curso no encontrado")
        return
    }
    val iconId = getIconForImageName(course.imagenNombre)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = "Logo",
            modifier = Modifier
                .padding(top = 24.dp)
                .size(120.dp, 120.dp)
        )
        Text(course.nombre, fontFamily = FontFamily(Font(R.font.murecho_bold)), fontSize = 20.sp,color = colorResource(id = R.color.white))
        Spacer(Modifier.height(8.dp))
        Text(course.descripcion, fontSize = 16.sp,color = colorResource(id = R.color.white))

        Spacer(Modifier.height(12.dp))
        Column(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalAlignment = Alignment.Start) {
            Text(
                "Horario: ${Dia.from(course.dias)} ${formatTime(course.horaInicio)} - ${
                    formatTime(
                        course.horaFin
                    )
                }",color = colorResource(id = R.color.white),modifier = Modifier.padding(bottom = 4.dp)
            )
            Text("Fecha: ${formatDate(course.fechaInicio)} al ${formatDate(course.fechaFin)}",color = colorResource(id = R.color.white),modifier = Modifier.padding(bottom = 4.dp))
            Text("Créditos: ${course.creditos}",color = colorResource(id = R.color.white),modifier = Modifier.padding(bottom = 4.dp))
            Text("Capacidad: ${course.capacidad}",color = colorResource(id = R.color.white),modifier = Modifier.padding(bottom = 4.dp))
            Text("Departamento: ${course.departamentoNombre}",color = colorResource(id = R.color.white),modifier = Modifier.padding(bottom = 4.dp))
            Text("Carreras: ${course.carreraNombres.joinToString()}",color = colorResource(id = R.color.white),modifier = Modifier.padding(bottom = 4.dp))
            Text("Tipo de actividad: ${TipoActividad.from(course.tipoActividad)}",color = colorResource(id = R.color.white),modifier = Modifier.padding(bottom = 4.dp))
        }

        Spacer(Modifier.height(24.dp))
        Button(onClick = { CDetailViewModel.inscribir(onEnrollSuccess,userId, course.id) }) {
            Text("Inscribirme")
        }

    }
}

