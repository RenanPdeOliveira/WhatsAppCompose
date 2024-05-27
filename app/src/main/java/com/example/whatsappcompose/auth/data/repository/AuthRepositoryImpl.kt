package com.example.whatsappcompose.auth.data.repository

import com.example.whatsappcompose.auth.domain.AuthError
import com.example.whatsappcompose.auth.domain.repository.AuthRepository
import com.example.whatsappcompose.core.domain.Result
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

class AuthRepositoryImpl(
    private val auth: FirebaseAuth
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<AuthResult, AuthError.Exceptions> {
        return withContext(Dispatchers.IO) {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            try {
                Result.Success(data = result)
            } catch (e: Exception) {
                e.printStackTrace()
                if (e is CancellationException) throw e
                Result.Error(error = AuthError.Exceptions.KOTLIN_EXCEPTION)
            } catch (weakPassword: FirebaseAuthWeakPasswordException) {
                weakPassword.printStackTrace()
                Result.Error(error = AuthError.Exceptions.WEAK_PASSWORD_EXCEPTION)
            } catch (collision: FirebaseAuthUserCollisionException) {
                collision.printStackTrace()
                Result.Error(error = AuthError.Exceptions.COLLISION_EXCEPTION)
            } catch (credentials: FirebaseAuthInvalidCredentialsException) {
                credentials.printStackTrace()
                Result.Error(error = AuthError.Exceptions.CREDENTIALS_EXCEPTION)
            }
        }
    }
}