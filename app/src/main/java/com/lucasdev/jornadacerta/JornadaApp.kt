package com.lucasdev.jornadacerta

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lucasdev.jornadacerta.screens.history.ui.HistoryScreen
import com.lucasdev.jornadacerta.screens.register.presentation.ui.RegisterScreen

@Composable
fun JornadaApp() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "register"){
        composable(route = "register") {
            RegisterScreen(
                navController = navController
            )
        }

        composable(route = "history") {
            HistoryScreen(
                navController = navController
            )
        }
    }
    
}

