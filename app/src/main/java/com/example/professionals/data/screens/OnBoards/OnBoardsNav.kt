package com.example.professionals.data.screens.OnBoards

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "screen1"
    ) {
        composable("screen1") { OnBoard1(navController) }
        composable("screen2") { OnBoard2(navController) }
        composable("screen3") { OnBoard3(navController) }
    }
}