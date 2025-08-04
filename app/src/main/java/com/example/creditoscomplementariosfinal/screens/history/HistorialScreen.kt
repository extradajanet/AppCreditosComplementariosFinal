package com.example.creditoscomplementariosfinal.screens.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.creditoscomplementariosfinal.R
import com.example.creditoscomplementariosfinal.domain.model.AlumnoActividadResponse
import com.example.creditoscomplementariosfinal.ui.utilities.Estados
import com.example.creditoscomplementariosfinal.ui.components.ScreenFooter
import com.example.creditoscomplementariosfinal.ui.components.ScreenHeader
import com.example.creditoscomplementariosfinal.ui.utilities.formatDate
import com.example.creditoscomplementariosfinal.ui.utilities.getIconForImageName

@Composable
fun HistorialScreen( userId: Int,navController: NavController,viewModel: HistoryViewModel = viewModel()) {
    val loginColor1 = colorResource(id = R.color.Login1)
    val loginColor2 = colorResource(id = R.color.Login2)
    val history by viewModel.history.collectAsState()
    val hasLoaded = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!hasLoaded.value) {
            viewModel.fetchHistory(userId)
            hasLoaded.value = true
        }
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
                title = "Historial",
                showBackButton = true,
                onBackClick = { navController.navigate("home/$userId") }
            )
            HistoryDetails(userId = userId,history = history,viewModel = viewModel)
            ScreenFooter(
                onHomeClick = { navController.navigate("home/$userId") },
                onHistoryClick = { navController.navigate("historial/$userId") },
                onProfileClick = { navController.navigate("perfil/$userId")  }
            )
        }
    }
}

@Composable
fun HistoryDetails(userId: Int,history: List<AlumnoActividadResponse>,viewModel: HistoryViewModel){
    LazyColumn(
        modifier = Modifier
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(history) { course ->
            val iconId = getIconForImageName(course.imagenNombre)
            Card(
                onClick = {},
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

                        Row(modifier = Modifier.padding(4.dp)) {

                            Text(
                                text = "Fecha Inicio: ",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = " ${formatDate(course.fechaInicio)} ",
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
                        Row(modifier = Modifier.padding(4.dp),) {

                            Text(
                                text = "Estado: ",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "${Estados.from(course.estadoActividad)}",
                                color = Color.Black
                            )
                        }


                    }
                    IconButton(onClick ={viewModel.deleteHistory(alumnoId = userId,actividadId = course.actividadId)}) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Darse de baja",
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }

}


