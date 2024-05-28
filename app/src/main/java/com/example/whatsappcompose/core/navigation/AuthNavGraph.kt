package com.example.whatsappcompose.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.whatsappcompose.auth.presentation.reset_password.ForgotPasswordScreen
import com.example.whatsappcompose.auth.presentation.sign_in.SignInScreen
import com.example.whatsappcompose.auth.presentation.sign_up.SignUpScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation<Screens.Auth>(
        startDestination = Screens.SignInScreen
    ) {
        composable<Screens.SignInScreen> {
            SignInScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
        composable<Screens.SignUpScreen> {
            SignUpScreen(
                onNavigate = {
                    navController.navigate(it.route)
                },
                popBackStack = {
                    navController.popBackStack()
                }
            )
        }
        composable<Screens.ForgotPasswordScreen> {
            ForgotPasswordScreen(
                popBackStack = {
                    navController.popBackStack()
                }
            )
        }
    }
}