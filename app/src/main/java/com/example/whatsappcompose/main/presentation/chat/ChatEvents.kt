package com.example.whatsappcompose.main.presentation.chat

sealed interface ChatEvents {
    data object OnNavigateBack : ChatEvents
}