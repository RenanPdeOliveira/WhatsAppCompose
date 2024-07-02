package com.example.whatsappcompose.auth.domain.use_cases

import com.example.whatsappcompose.auth.domain.util.AuthError
import com.example.whatsappcompose.auth.domain.repository.AuthRepository
import com.example.whatsappcompose.core.domain.util.Result
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(name: String, email: String, password: String): Result<AuthResult, AuthError.SignUp> {
        if (name.isEmpty() && email.isEmpty() && password.isEmpty()) {
            return Result.Error(error = AuthError.SignUp.Fields.NAME_EMAIL_PASSWORD_EMPTY)
        }
        if (name.isEmpty()) {
            return Result.Error(error = AuthError.SignUp.Fields.NAME_EMPTY)
        }
        if (email.isEmpty()) {
            return Result.Error(error = AuthError.SignUp.Fields.EMAIL_EMPTY)
        }
        if (password.isEmpty()) {
            return Result.Error(error = AuthError.SignUp.Fields.PASSWORD_EMPTY)
        }
        return repository.signUp(name, email, password)
    }
}