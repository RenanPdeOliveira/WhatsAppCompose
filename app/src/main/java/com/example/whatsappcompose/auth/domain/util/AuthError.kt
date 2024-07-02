package com.example.whatsappcompose.auth.domain.util

import com.example.whatsappcompose.core.domain.util.Error

sealed interface AuthError: Error {

    sealed interface SignIn: AuthError {
        enum class Fields: SignIn {
            EMAIL_PASSWORD_EMPTY,
            EMAIL_EMPTY,
            PASSWORD_EMPTY
        }

        enum class Exceptions: SignIn {
            KOTLIN_EXCEPTION,
            WEAK_PASSWORD_EXCEPTION,
            COLLISION_EXCEPTION,
            CREDENTIALS_EXCEPTION
        }
    }

    sealed interface SignUp: AuthError {
        enum class Fields: SignUp {
            NAME_EMAIL_PASSWORD_EMPTY,
            NAME_EMPTY,
            EMAIL_EMPTY,
            PASSWORD_EMPTY
        }

        enum class Exceptions: SignUp {
            KOTLIN_EXCEPTION,
            WEAK_PASSWORD_EXCEPTION,
            COLLISION_EXCEPTION,
            CREDENTIALS_EXCEPTION
        }
    }

    sealed interface ResetPassword: AuthError {
        enum class Fields: ResetPassword {
            EMAIL_EMPTY
        }

        enum class Exceptions: ResetPassword {
            KOTLIN_EXCEPTION
        }
    }
}