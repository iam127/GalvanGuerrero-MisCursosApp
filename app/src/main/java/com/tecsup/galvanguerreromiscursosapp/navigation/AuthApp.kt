package com.tecsup.galvanguerreromiscursosapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecsup.galvanguerreromiscursosapp.screens.CursosListScreen
import com.tecsup.galvanguerreromiscursosapp.screens.LoginScreen
import com.tecsup.galvanguerreromiscursosapp.screens.RegisterScreen

object Destinations {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val CURSOS_LIST = "cursos_list"
}

@Composable
fun AuthApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.LOGIN
    ) {
        composable(Destinations.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Destinations.CURSOS_LIST) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Destinations.REGISTER)
                }
            )
        }

        composable(Destinations.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Destinations.CURSOS_LIST) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Destinations.CURSOS_LIST) {
            CursosListScreen(
                onLogout = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.CURSOS_LIST) { inclusive = true }
                    }
                }
            )
        }
    }
}