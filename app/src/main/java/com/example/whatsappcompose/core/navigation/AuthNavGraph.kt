package com.example.whatsappcompose.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.whatsappcompose.auth.presentation.ForgotPasswordScreen
import com.example.whatsappcompose.auth.presentation.LoginScreen
import com.example.whatsappcompose.auth.presentation.SignUpScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation<Screens.Auth>(
        startDestination = Screens.LoginScreen
    ) {
        composable<Screens.LoginScreen> {
            LoginScreen(onEvent = {
                navController.navigate(it.route)
            })
        }
        composable<Screens.SignUpScreen> {
            SignUpScreen(popBackStack = {
                navController.popBackStack()
            })
        }
        composable<Screens.ForgotPasswordScreen> {
            ForgotPasswordScreen(popBackStack = {
                navController.popBackStack()
            })
        }
    }
}