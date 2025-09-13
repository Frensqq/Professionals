package com.example.professionals.data.screens.authentication

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun AuthNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "RegAcc"
    ) {
        composable("RegAcc") { RegisterAccount(navController) }
        composable("SingIn") { SingIn(navController) }
        composable("ForgPass") { ForgotPassword(navController) }
        composable("Verification/{text}/{otp}") {  backStackEntry ->
            // Получаем переданный текст
            val text = backStackEntry.arguments?.getString("text") ?: ""
            val otp = backStackEntry.arguments?.getString("otp") ?: ""
            Verification(text, otp, navController)
        }
        composable("CreateNewPass/{token}/{id}") {backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val id = backStackEntry.arguments?.getString("id") ?: ""
            CreateNewPassword(token,id,navController) }
    }
}