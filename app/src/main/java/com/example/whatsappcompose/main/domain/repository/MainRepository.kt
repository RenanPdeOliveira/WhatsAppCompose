package com.example.whatsappcompose.main.domain.repository

import com.example.whatsappcompose.core.domain.util.Result
import com.example.whatsappcompose.core.domain.User
import com.example.whatsappcompose.main.domain.util.MainError
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getUserData(): Flow<Result<User, MainError.Exception>>
}