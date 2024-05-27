package com.example.whatsappcompose.auth.domain

import com.example.whatsappcompose.core.domain.Error

sealed interface AuthError: Error {
    enum class Exceptions: AuthError {
        KOTLIN_EXCEPTION,
        WEAK_PASSWORD_EXCEPTION,
        COLLISION_EXCEPTION,
        CREDENTIALS_EXCEPTION
    }

    enum class Fields: AuthError {
        EMAIL_EMPTY,
        PASSWORD_EMPTY,
        EMAIL_PASSWORD_EMPTY
    }
}