package com.example.whatsappcompose.auth.domain

import com.example.whatsappcompose.core.domain.Error

sealed interface AuthError: Error {

    sealed interface SignIn: AuthError {
        enum class Exceptions: SignIn {
            KOTLIN_EXCEPTION,
            WEAK_PASSWORD_EXCEPTION,
            COLLISION_EXCEPTION,
            CREDENTIALS_EXCEPTION
        }

        enum class Fields: SignIn {
            EMAIL_EMPTY,
            PASSWORD_EMPTY,
            EMAIL_PASSWORD_EMPTY
        }
    }

    sealed interface SignUp: AuthError {
        enum class Exceptions: SignUp {
            KOTLIN_EXCEPTION,
            WEAK_PASSWORD_EXCEPTION,
            COLLISION_EXCEPTION,
            CREDENTIALS_EXCEPTION
        }

        enum class Fields: SignUp {
            NAME_EMPTY,
            EMAIL_EMPTY,
            PASSWORD_EMPTY,
            NAME_EMAIL_PASSWORD_EMPTY
        }
    }
}