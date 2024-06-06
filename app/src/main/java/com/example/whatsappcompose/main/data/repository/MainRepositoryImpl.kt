package com.example.whatsappcompose.main.data.repository

import com.example.whatsappcompose.core.domain.Result
import com.example.whatsappcompose.core.domain.User
import com.example.whatsappcompose.main.domain.MainError
import com.example.whatsappcompose.main.domain.repository.MainRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : MainRepository {
    override fun getUserData(): Flow<Result<User, MainError.Exception>> {
        return callbackFlow {
            val id = auth.currentUser?.uid
            id?.let {
                db.collection("users")
                    .document(id)
                    .get()
                    .addOnSuccessListener { document ->
                        val result = document.data
                        result?.let {
                            val name = result["name"].toString()
                            val photo = result["photo"].toString()
                            trySend(Result.Success(data = User(name = name, photo = photo)))
                        }
                    }.addOnFailureListener {
                        trySend(Result.Error(error = MainError.Exception.KOTLIN_EXCEPTION))
                    }
            }
            awaitClose {
                close()
            }
        }
    }
}