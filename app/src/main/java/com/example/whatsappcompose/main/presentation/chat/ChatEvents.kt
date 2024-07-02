package com.example.whatsappcompose.main.presentation.chat

sealed interface ChatEvents {
    data class OnContactClick(val name: String, val photo: String): ChatEvents
}