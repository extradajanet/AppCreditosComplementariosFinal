package com.example.creditoscomplementariosfinal.screens.cursos


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.creditoscomplementariosfinal.R
import com.example.creditoscomplementariosfinal.domain.model.CourseResponse
import com.example.creditoscomplementariosfinal.ui.utilities.Dia

import com.example.creditoscomplementariosfinal.ui.components.ScreenFooter
import com.example.creditoscomplementariosfinal.ui.components.ScreenHeader
import com.example.creditoscomplementariosfinal.ui.utilities.formatDate
import com.example.creditoscomplementariosfinal.ui.utilities.formatTime
import com.example.creditoscomplementariosfinal.ui.utilities.getIconForImageName

// Usando viewmodel sin prog funcional
/*
    @Composable
    fun CoursesScreen(
        viewModel: CoursesViewModel = viewModel(),
        navController: NavController,
        userId: Int
    ) {
        val loginColor1 = colorResource(id = R.color.Login1)
        val loginColor2 = colorResource(id = R.color.Login2)
        val courses by viewModel.courses.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.fetchCourses()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(colors = listOf(loginColor1, loginColor2))
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenHeader(
                title = "Cursos Disponibles",
                showBackButton = true,
                onBackClick = { navController.navigate("home/$userId") }
            )
            if (courses.isEmpty()) {
                Text("No hay cursos disponibles", modifier = Modifier.padding(16.dp))
            } else {
                CourseOptions(
                    courses = courses,
                    modifier = Modifier.weight(1f),
                    navController = navController,
                    userId = userId
                )
            }
            ScreenFooter(
                onHomeClick = { navController.navigate("home/$userId") },
                onHistoryClick = { navController.navigate("historial/$userId") },
                onProfileClick = { navController.navigate("perfil/$userId") }
            )

        }
    }
*/
@Composable
fun CoursesScreen(
    navController: NavController,
    userId: Int,
    repository: CourseRepository = CourseRepositoryImpl()
) {
    val loginColor1 = colorResource(id = R.color.Login1)
    val loginColor2 = colorResource(id = R.color.Login2)

    val coursesResult by produceState<Result<List<CourseResponse>>>(initialValue = Result.success(emptyList())) {
        value = loadCourses(repository)
    }

    val courses = coursesResult.getOrElse { emptyList() }
    val error = coursesResult.exceptionOrNull()?.localizedMessage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(loginColor1, loginColor2))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader(
            title = "Cursos Disponibles",
            showBackButton = true,
            onBackClick = { navController.navigate("home/$userId") }
        )

        when {
            error != null -> Text("Error: $error", color = Color.Red, modifier = Modifier.padding(16.dp))
            courses.isEmpty() -> Text(stringResource(R.string.no_hay_cursos_disponibles), modifier = Modifier.padding(16.dp))
            else -> CourseOptions(courses, Modifier.weight(1f), navController, userId)
        }

        ScreenFooter(
            onHomeClick = { navController.navigate("home/$userId") },
            onHistoryClick = { navController.navigate("historial/$userId") },
            onProfileClick = { navController.navigate("perfil/$userId") }
        )
    }
}


@Composable
fun CourseOptions(courses: List<CourseResponse>, modifier: Modifier,navController: NavController,userId: Int) {
    LazyColumn(
        modifier = modifier
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(courses) { course ->
            val diaNombre = Dia.from(course.dias)
            val iconId = getIconForImageName(course.imagenNombre)
            Card(
                onClick = { navController.navigate("course_detail/${course.id}/$userId")
                },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Row {
                    Image(
                        painter = painterResource(id = iconId),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.25f)
                            .padding(8.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Column {
                        Text(
                            text = course.nombre,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(8.dp),
                            color = Color.Black
                        )
                        Row(modifier = Modifier.padding(4.dp),) {

                            Text(
                                text = "Horario : ",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "${diaNombre} ${formatTime(course.horaInicio)} - ${formatTime(course.horaFin)}",
                                color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(4.dp),) {

                            Text(
                                text = "Fecha Inicio : ",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "${formatDate(course.fechaInicio)}",
                                color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(4.dp),) {

                            Text(
                                text = "Créditos : ",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "${course.creditos}",
                                color = Color.Black
                            )
                        }

                    }
                }
            }
        }
    }
}



