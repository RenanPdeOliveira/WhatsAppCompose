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
    data class ProfileScreen(val name: String, val photo: String): Screens
    @Serializable
    data class ChatScreen(val name: String, val photo: String? = null): Screens
}