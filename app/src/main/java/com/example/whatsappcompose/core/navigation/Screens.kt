package com.example.whatsappcompose.core.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object Auth: Screens
    @Serializable
    data object Main: Screens
    @Serializable
    data object SignInScreen: Screens
    @Serializable
    data object SignUpScreen: Screens
    @Serializable
    data object ForgotPasswordScreen: Screens
    @Serializable
    data object MainScreen: Screens
    @Serializable
    data object ProfileScreen: Screens
}