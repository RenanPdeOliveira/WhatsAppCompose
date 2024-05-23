package com.example.whatsappcompose.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object Auth: Screens
    @Serializable
    data object LoginScreen: Screens
    @Serializable
    data object SignUpScreen: Screens
}