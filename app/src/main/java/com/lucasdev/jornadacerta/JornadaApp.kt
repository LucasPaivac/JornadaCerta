package com.lucasdev.jornadacerta

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lucasdev.jornadacerta.screens.history.ui.HistoryScreen
import com.lucasdev.jornadacerta.screens.register.presentation.ui.RegisterScreen

@Composable
fun JornadaApp() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar(
                tonalElevation = 0.dp
            ) {
                NavigationBarItem(
                    selected = currentRoute == "register",
                    onClick = {
                        if (currentRoute != "register") {
                            navController.navigate("register") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        val isSelected = currentRoute == "register"

                        Icon(
                            painter = painterResource(
                                if (isSelected)
                                    R.drawable.ic_home_filled else R.drawable.ic_home_outlined
                            ),
                            contentDescription = "Home bottom bar icon"
                        )
                    },
                    label = { Text("Início") }
                )
                NavigationBarItem(
                    selected = currentRoute == "history",
                    onClick = {
                        if (currentRoute != "history") {
                            navController.navigate("history") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_history),
                            contentDescription = "History bottom bar icon"
                        )
                    },
                    label = { Text("Histórico") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "register",
            modifier = Modifier.padding(innerPadding)
        ) {
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


}

