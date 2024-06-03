package com.example.whatsappcompose.auth.presentation.sign_up

sealed interface SignUpEvents {
    data class OnSignUpButtonClick(val name: String, val email: String, val password: String):
        SignUpEvents
    data object OnNavigateBackClick: SignUpEvents
}