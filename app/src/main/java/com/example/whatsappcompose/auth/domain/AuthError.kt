package com.example.whatsappcompose.auth.domain

import com.example.whatsappcompose.core.domain.Error

sealed interface AuthError: Error {
    data object LoginError: AuthError
}