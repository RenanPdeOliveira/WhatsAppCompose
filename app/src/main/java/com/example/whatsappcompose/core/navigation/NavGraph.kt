package com.example.whatsappcompose.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph(
    navHostController: NavHostController,
    auth: FirebaseAuth
) {
    NavHost(
        navController = navHostController,
        startDestination = if (auth.currentUser != null) Screens.Main else Screens.Auth
    ) {
        authNavGraph(navController = navHostController)
        mainNavGraph(navController = navHostController)
    }
}