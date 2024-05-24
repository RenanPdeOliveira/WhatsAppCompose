package com.example.whatsappcompose.auth.data.repository

import com.example.whatsappcompose.auth.domain.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val auth: FirebaseAuth
) : AuthRepository {
    override fun login(email: String, password: String): Flow<AuthResult> {
        return flow {
            auth.signInWithEmailAndPassword(email, password)
        }
    }
}