package com.example.whatsappcompose.auth.presentation.reset_password

sealed interface ResetPasswordEvents {
    data class OnResetButtonClick(val email: String): ResetPasswordEvents
    data object OnNavigateBackClick: ResetPasswordEvents
}