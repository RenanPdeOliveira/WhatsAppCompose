package com.example.whatsappcompose.main.data.repository

import androidx.core.net.toUri
import com.example.whatsappcompose.core.domain.Result
import com.example.whatsappcompose.core.domain.User
import com.example.whatsappcompose.main.domain.ProfileError
import com.example.whatsappcompose.main.domain.repository.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ProfileRepository {
    override suspend fun signOut(): Result<Unit, ProfileError.Exception> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(data = auth.signOut())
            } catch (e: Exception) {
                e.printStackTrace()
                if (e is CancellationException) throw e
                Result.Error(error = ProfileError.Exception.KOTLIN_EXCEPTION)
            }
        }
    }

    override fun saveChanges(
        name: String,
        photo: String
    ): Flow<Result<User, ProfileError.Exception>> {
        return callbackFlow {
            val id = auth.currentUser?.uid
            id?.let {
                storage
                    .getReference("photos")
                    .child("users")
                    .child(id)
                    .child("profile.jpg")
                    .putFile(photo.toUri())
                    .addOnSuccessListener {
                        it.metadata?.reference?.downloadUrl
                            ?.addOnSuccessListener { uri ->
                                val data = mapOf(
                                    "name" to name,
                                    "photo" to uri.toString()
                                )
                                firestore
                                    .collection("users")
                                    .document(id)
                                    .update(data)
                            }
                        trySend(Result.Success(data = User(name = name, photo = photo)))
                    }.addOnFailureListener {
                        trySend(Result.Error(error = ProfileError.Exception.KOTLIN_EXCEPTION))
                    }
            }
            awaitClose {
                close()
            }
        }
    }
}