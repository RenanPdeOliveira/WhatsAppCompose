package com.example.whatsappcompose.auth.data.repository

import android.util.Log
import com.example.whatsappcompose.auth.domain.AuthError
import com.example.whatsappcompose.auth.domain.repository.AuthRepository
import com.example.whatsappcompose.core.domain.Result
import com.example.whatsappcompose.core.domain.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<AuthResult, AuthError.SignIn.Exceptions> {
        return withContext(Dispatchers.IO) {
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                Result.Success(data = result)
            } catch (e: Exception) {
                e.printStackTrace()
                if (e is CancellationException) throw e
                Result.Error(error = AuthError.SignIn.Exceptions.KOTLIN_EXCEPTION)
            } catch (weakPassword: FirebaseAuthWeakPasswordException) {
                weakPassword.printStackTrace()
                Result.Error(error = AuthError.SignIn.Exceptions.WEAK_PASSWORD_EXCEPTION)
            } catch (collision: FirebaseAuthUserCollisionException) {
                collision.printStackTrace()
                Result.Error(error = AuthError.SignIn.Exceptions.COLLISION_EXCEPTION)
            } catch (credentials: FirebaseAuthInvalidCredentialsException) {
                credentials.printStackTrace()
                Result.Error(error = AuthError.SignIn.Exceptions.CREDENTIALS_EXCEPTION)
            }
        }
    }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Result<AuthResult, AuthError.SignUp.Exceptions> {
        return withContext(Dispatchers.IO) {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val id = result.user?.uid
                if (id != null) {
                    addUser(id, name, email)
                }
                Result.Success(data = result)
            } catch (e: Exception) {
                e.printStackTrace()
                if (e is CancellationException) throw e
                Result.Error(error = AuthError.SignUp.Exceptions.KOTLIN_EXCEPTION)
            } catch (weakPassword: FirebaseAuthWeakPasswordException) {
                weakPassword.printStackTrace()
                Result.Error(error = AuthError.SignUp.Exceptions.WEAK_PASSWORD_EXCEPTION)
            } catch (collision: FirebaseAuthUserCollisionException) {
                collision.printStackTrace()
                Result.Error(error = AuthError.SignUp.Exceptions.COLLISION_EXCEPTION)
            } catch (credentials: FirebaseAuthInvalidCredentialsException) {
                credentials.printStackTrace()
                Result.Error(error = AuthError.SignUp.Exceptions.CREDENTIALS_EXCEPTION)
            }
        }
    }

    private fun addUser(id: String, name: String, email: String) {
        val user = User(id, name, email)
        firestore.collection("users")
            .document(id)
            .set(user)
            .addOnSuccessListener {
                Log.d("firestoreTest", "Dado incluido")
            }.addOnFailureListener {
                Log.d("firestoreTest", "Dado nao incluido ${it.message}")
            }
    }
}