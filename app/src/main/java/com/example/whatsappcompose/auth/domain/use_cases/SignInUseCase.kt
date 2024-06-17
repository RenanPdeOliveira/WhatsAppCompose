package com.example.whatsappcompose.auth.domain.use_cases

import com.example.whatsappcompose.auth.domain.util.AuthError
import com.example.whatsappcompose.auth.domain.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import com.example.whatsappcompose.core.domain.util.Result
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthResult, AuthError.SignIn> {
        if (email.isEmpty() && password.isEmpty()) {
            return Result.Error(AuthError.SignIn.Fields.EMAIL_PASSWORD_EMPTY)
        }
        if (email.isEmpty()) {
            return Result.Error(AuthError.SignIn.Fields.EMAIL_EMPTY)
        }
        if (password.isEmpty()) {
            return Result.Error(AuthError.SignIn.Fields.PASSWORD_EMPTY)
        }
        return repository.login(email, password)
    }
}