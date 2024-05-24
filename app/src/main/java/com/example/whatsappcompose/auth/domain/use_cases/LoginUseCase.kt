package com.example.whatsappcompose.auth.domain.use_cases

import com.example.whatsappcompose.auth.domain.AuthError
import com.example.whatsappcompose.auth.domain.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import com.example.whatsappcompose.core.domain.Result

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend fun emailAndPasswordUseCase(email: String, password: String): Result<AuthResult, AuthError> {
        repository.login(email, password).collect {
            return@collect Result.Success(data = it)
        }
    }
}