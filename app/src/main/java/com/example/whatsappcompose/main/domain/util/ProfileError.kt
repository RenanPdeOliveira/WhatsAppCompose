package com.example.whatsappcompose.main.domain.util

import com.example.whatsappcompose.core.domain.util.Error

sealed interface ProfileError: Error {
    enum class Exception: ProfileError {
        KOTLIN_EXCEPTION
    }
    enum class Fields: ProfileError {
        NAME_EMPTY,
        PHOTO_EMPTY
    }
}