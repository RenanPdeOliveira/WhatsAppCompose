package com.example.whatsappcompose.main.domain

import com.example.whatsappcompose.core.domain.Error

sealed interface ProfileError: Error {
    enum class Exception: ProfileError {
        KOTLIN_EXCEPTION
    }
    enum class Fields: ProfileError {
        NAME_EMPTY,
        PHOTO_EMPTY
    }
}