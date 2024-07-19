package com.example.whatsappcompose.main.presentation.chat

sealed interface ChatEvents {
    data object OnNavigateBack : ChatEvents
    data class OnSendMessageClick(val message: String, val userId: String): ChatEvents
}