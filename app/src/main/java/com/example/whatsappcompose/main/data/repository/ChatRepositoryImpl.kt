package com.example.whatsappcompose.main.data.repository

import com.example.whatsappcompose.core.domain.constants.Constants
import com.example.whatsappcompose.core.domain.util.Result
import com.example.whatsappcompose.main.domain.repository.ChatRepository
import com.example.whatsappcompose.main.domain.util.ChatError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
): ChatRepository {
    override fun saveMessage(message: String, userId: String): Result<Unit, ChatError.Exception> {
        return try {
            val currentUserId = auth.currentUser?.uid
            if (!currentUserId.isNullOrEmpty()) {
                db
                    .collection(Constants.MESSAGES)
                    .document(currentUserId)
                    .collection(userId)
                    .add(message)
            }
            Result.Success(data = Unit)
        } catch (e: Exception) {
            Result.Error(error = ChatError.Exception.KOTLIN_EXCEPTION)
        }
    }
}