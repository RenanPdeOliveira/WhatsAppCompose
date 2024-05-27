package com.example.whatsappcompose.auth.domain.use_cases

import com.example.whatsappcompose.auth.domain.AuthError
import com.example.whatsappcompose.auth.domain.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import com.example.whatsappcompose.core.domain.Result

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthResult, AuthError> {
        if (email.isEmpty() && password.isEmpty()) {
            return Result.Error(AuthError.Fields.EMAIL_PASSWORD_EMPTY)
        }
        if (email.isEmpty()) {
            return Result.Error(AuthError.Fields.EMAIL_EMPTY)
        }
        if (password.isEmpty()) {
            return Result.Error(AuthError.Fields.PASSWORD_EMPTY)
        }
        return repository.login(email, password)
    }
}