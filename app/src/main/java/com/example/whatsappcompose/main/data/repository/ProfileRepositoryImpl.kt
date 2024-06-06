package com.example.whatsappcompose.main.data.repository

import androidx.core.net.toUri
import com.example.whatsappcompose.core.domain.Result
import com.example.whatsappcompose.main.domain.ProfileError
import com.example.whatsappcompose.main.domain.repository.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
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

    override suspend fun saveChanges(
        name: String,
        photo: String
    ): Result<Unit, ProfileError.Exception> {
        return withContext(Dispatchers.IO) {
            try {
                val id = auth.currentUser?.uid
                if (id != null) {
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
                        }
                }
                Result.Success(data = Unit)
            } catch (e: Exception) {
                e.printStackTrace()
                if (e is CancellationException) throw e
                Result.Error(error = ProfileError.Exception.KOTLIN_EXCEPTION)
            }
        }
    }
}