package com.example.creditoscomplementariosfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.creditoscomplementariosfinal.navigation.AppNavigation
import com.example.creditoscomplementariosfinal.screens.cursos.CoursesScreen
import com.example.creditoscomplementariosfinal.screens.home.HomeScreen
import com.example.creditoscomplementariosfinal.screens.login.LoginScreen
import com.example.creditoscomplementariosfinal.ui.theme.CreditosComplementariosFinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CreditosComplementariosFinalTheme {

                //CoursesScreen()
                    AppNavigation()

            }
        }
    }
}

enum class TipoActividad(val numero: Int, val displayName: String) {
    DEPORTIVO(1, "Deportivo"),
    CULTURAL(2, "Cultural"),
    TUTORIAS(3, "Tutorías"),
    MOOC(4, "MOOC");

    companion object {
        fun from(numero: Int): String {
            return values().firstOrNull { it.numero == numero }?.displayName ?: "Desconocido"
        }
    }
}