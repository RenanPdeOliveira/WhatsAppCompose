package com.example.whatsappcompose.main.domain.repository

import com.example.whatsappcompose.core.domain.Result
import com.example.whatsappcompose.core.domain.User
import com.example.whatsappcompose.main.domain.ProfileError
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun signOut(): Result<Unit, ProfileError.Exception>
    fun saveChanges(name: String, photo: String): Flow<Result<User, ProfileError.Exception>>
}