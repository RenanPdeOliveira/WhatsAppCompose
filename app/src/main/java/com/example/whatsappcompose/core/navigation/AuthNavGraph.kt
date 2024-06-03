package com.example.whatsappcompose.core.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.whatsappcompose.auth.presentation.reset_password.ForgotPasswordScreen
import com.example.whatsappcompose.auth.presentation.reset_password.ResetPasswordViewModel
import com.example.whatsappcompose.auth.presentation.sign_in.SignInScreen
import com.example.whatsappcompose.auth.presentation.sign_in.SignInViewModel
import com.example.whatsappcompose.auth.presentation.sign_up.SignUpScreen
import com.example.whatsappcompose.auth.presentation.sign_up.SignUpViewModel

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation<Screens.Auth>(
        startDestination = Screens.SignInScreen
    ) {
        composable<Screens.SignInScreen> {
            val viewModel = hiltViewModel<SignInViewModel>()
            val state = viewModel.state.collectAsState()
            SignInScreen(
                onNavigate = {
                    navController.navigate(it.route)
                },
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                state = state
            )
        }
        composable<Screens.SignUpScreen> {
            val viewModel = hiltViewModel<SignUpViewModel>()
            val state = viewModel.state.collectAsState()
            SignUpScreen(
                onNavigate = {
                    navController.navigate(it.route)
                },
                popBackStack = {
                    navController.popBackStack()
                },
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                state = state
            )
        }
        composable<Screens.ForgotPasswordScreen> {
            val viewModel = hiltViewModel<ResetPasswordViewModel>()
            val state = viewModel.state.collectAsState()
            ForgotPasswordScreen(
                popBackStack = {
                    navController.popBackStack()
                },
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                state = state
            )
        }
    }
}