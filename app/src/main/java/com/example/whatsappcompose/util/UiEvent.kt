package com.example.whatsappcompose.util

import com.example.whatsappcompose.navigation.Screens

sealed interface UiEvent {
    data class Navigate(val route: Screens): UiEvent
    data object PopBackStack: UiEvent
}