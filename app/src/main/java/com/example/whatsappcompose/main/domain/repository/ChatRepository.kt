package com.example.whatsappcompose.main.domain.repository

import com.example.whatsappcompose.core.domain.util.Result
import com.example.whatsappcompose.main.domain.util.ChatError

interface ChatRepository {
    fun saveMessage(message: String, userId: String): Result<Unit, ChatError.Exception>
}