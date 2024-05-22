package com.example.whatsappcompose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.whatsappcompose.LoginScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation<Auth>(
        startDestination = LoginScreen
    ) {
        composable<LoginScreen> {
            LoginScreen()
        }
    }
}