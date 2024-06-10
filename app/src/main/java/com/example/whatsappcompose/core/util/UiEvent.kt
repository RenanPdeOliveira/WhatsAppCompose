package com.example.whatsappcompose.core.util

import com.example.whatsappcompose.core.navigation.Screens

sealed interface UiEvent {
    data class Navigate(val route: Screens) : UiEvent
    data object PopBackStack : UiEvent
    data object OpenGallery : UiEvent
    data class ShowSnackBar(val uiText: UiText, val action: String? = null) : UiEvent
    data class ShowDialog(val isOpen: Boolean) : UiEvent
}