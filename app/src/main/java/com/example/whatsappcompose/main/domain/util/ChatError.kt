package com.example.whatsappcompose.main.domain.util

import com.example.whatsappcompose.core.domain.util.Error

sealed interface ChatError: Error {
    enum class Exception: ChatError {
        KOTLIN_EXCEPTION
    }

    enum class Fields: ChatError {
        MESSAGE_EMPTY
    }
}