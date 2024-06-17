package com.example.whatsappcompose.main.domain.repository

import com.example.whatsappcompose.core.domain.util.Result
import com.example.whatsappcompose.core.domain.User
import com.example.whatsappcompose.main.domain.util.ProfileError
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun signOut(): Result<Unit, ProfileError.Exception>
    fun saveChanges(name: String, photo: String): Flow<Result<User, ProfileError.Exception>>
}