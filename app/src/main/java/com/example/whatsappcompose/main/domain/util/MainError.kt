package com.example.whatsappcompose.main.domain.util

import com.example.whatsappcompose.core.domain.util.Error

sealed interface MainError: Error {
    enum class Exception: MainError {
        KOTLIN_EXCEPTION
    }
}