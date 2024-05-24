package com.example.whatsappcompose.auth.domain.repository

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<AuthResult>
}