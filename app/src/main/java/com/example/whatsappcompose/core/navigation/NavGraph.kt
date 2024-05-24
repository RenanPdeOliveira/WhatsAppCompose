package com.example.whatsappcompose.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.Auth
    ) {
        authNavGraph(navController = navHostController)
        mainNavGraph()
    }
}