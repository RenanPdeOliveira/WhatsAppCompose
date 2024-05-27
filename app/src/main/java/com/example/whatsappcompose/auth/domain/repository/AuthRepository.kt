package com.example.whatsappcompose.auth.domain.repository

import com.example.whatsappcompose.auth.domain.AuthError
import com.example.whatsappcompose.core.domain.Result
import com.google.firebase.auth.AuthResult

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<AuthResult, AuthError.Exceptions>
}