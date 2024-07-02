package com.example.whatsappcompose.main.presentation.main

import com.example.whatsappcompose.core.domain.User

data class UserState(
    val user: User = User(),
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false
)
