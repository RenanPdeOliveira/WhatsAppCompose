package com.example.whatsappcompose.auth.domain.use_cases

import com.example.whatsappcompose.auth.domain.util.AuthError
import com.example.whatsappcompose.auth.domain.repository.AuthRepository
import com.example.whatsappcompose.core.domain.util.Result
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<Void, AuthError.ResetPassword> {
        if (email.isEmpty()) {
            return Result.Error(error = AuthError.ResetPassword.Fields.EMAIL_EMPTY)
        }
        return repository.resetPassword(email)
    }
}