package com.example.creditoscomplementariosfinal.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.creditoscomplementariosfinal.screens.coursedetail.CourseDetailScreen
import com.example.creditoscomplementariosfinal.screens.cursos.CoursesScreen
import com.example.creditoscomplementariosfinal.screens.cursos.CoursesViewModel
import com.example.creditoscomplementariosfinal.screens.history.HistorialScreen
import com.example.creditoscomplementariosfinal.screens.home.HomeScreen
import com.example.creditoscomplementariosfinal.screens.login.LoginScreen
import com.example.creditoscomplementariosfinal.screens.login.LoginViewModel
import com.example.creditoscomplementariosfinal.screens.perfil.PerfilScreen
import com.example.creditoscomplementariosfinal.screens.register.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()
    val coursesViewModel: CoursesViewModel = viewModel()


    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { alumnoId ->
                    navController.navigate("home/$alumnoId")
                }, navController = navController
            )
        }
        composable(
            route = "home/{alumnoId}",
            arguments = listOf(navArgument("alumnoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val alumnoId = backStackEntry.arguments?.getInt("alumnoId") ?: 0
            HomeScreen(userId = alumnoId, navController = navController)
        }
        composable("register") {
            RegisterScreen(onRegisterSuccess = {
                navController.navigate("login") {
                    popUpTo("register") { inclusive = true }
                }
            })
        }
        composable("courses/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: return@composable
            CoursesScreen(navController = navController, userId = userId)
        }

        composable("course_detail/{courseId}/{userId}") { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId")?.toIntOrNull() ?: return@composable
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: return@composable
            CourseDetailScreen(courseId = courseId, userId = userId, navController = navController, onEnrollSuccess = {
                navController.navigate("courses/${userId}"){
                    popUpTo("course_detail/{courseId}/{userId}"){inclusive =true}
                }
            })
        }
        composable("historial/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: return@composable
            HistorialScreen(navController = navController, userId = userId)
        }
        composable("perfil/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: return@composable
            PerfilScreen(navController = navController, userId = userId)
        }



    }
}
