package com.example.whatsappcompose.auth.domain.repository

import com.example.whatsappcompose.auth.domain.util.AuthError
import com.example.whatsappcompose.core.domain.util.Result
import com.google.firebase.auth.AuthResult

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<AuthResult, AuthError.SignIn.Exceptions>
    suspend fun signUp(name: String, email: String, password: String): Result<AuthResult, AuthError.SignUp.Exceptions>
    suspend fun resetPassword(email: String): Result<Void, AuthError.ResetPassword.Exceptions>
}