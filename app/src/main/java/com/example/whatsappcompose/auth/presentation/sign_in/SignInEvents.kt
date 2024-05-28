package com.example.whatsappcompose.auth.presentation.sign_in

sealed interface SignInEvents {
    data class OnSignInButtonClick(val email: String, val password: String): SignInEvents
    data object OnSignUpButtonClick: SignInEvents
    data object OnResetPasswordClick: SignInEvents
}