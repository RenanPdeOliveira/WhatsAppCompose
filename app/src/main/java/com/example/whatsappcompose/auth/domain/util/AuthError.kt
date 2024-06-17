package com.example.whatsappcompose.auth.domain.util

import com.example.whatsappcompose.core.domain.util.Error

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

    sealed interface ResetPassword: AuthError {
        enum class Exceptions: ResetPassword {
            KOTLIN_EXCEPTION
        }

        enum class Fields: ResetPassword {
            EMAIL_EMPTY
        }
    }
}