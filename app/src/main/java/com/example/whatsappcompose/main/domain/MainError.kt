package com.example.whatsappcompose.main.domain

import com.example.whatsappcompose.core.domain.Error

sealed interface MainError: Error {
    enum class Exception: MainError {
        KOTLIN_EXCEPTION
    }
}