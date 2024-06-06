package com.example.whatsappcompose.main.domain.repository

import com.example.whatsappcompose.core.domain.Result
import com.example.whatsappcompose.main.domain.ProfileError

interface ProfileRepository {
    suspend fun signOut(): Result<Unit, ProfileError.Exception>
    suspend fun saveChanges(name: String, photo: String): Result<Unit, ProfileError.Exception>
}