package com.example.whatsappcompose.main.data.repository

import android.util.Log
import com.example.whatsappcompose.core.domain.util.Result
import com.example.whatsappcompose.core.domain.User
import com.example.whatsappcompose.main.domain.util.MainError
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

    override fun getUsers(): Flow<Result<List<User>, MainError.Exception>> {
        return callbackFlow {
            val users = mutableListOf<User>()
            db.collection("users")
                .addSnapshotListener { value, error ->
                    error?.let {
                        trySend(Result.Error(error = MainError.Exception.KOTLIN_EXCEPTION))
                    }
                    val documents = value?.documents
                    documents?.forEach {
                        val user = it.toObject(User::class.java)
                        if (user != null) {
                            Log.d("userData", user.id)
                            users.add(user)
                        }
                    }
                    trySend(Result.Success(data = users))
                }
            awaitClose {
                close()
            }
        }
    }
}