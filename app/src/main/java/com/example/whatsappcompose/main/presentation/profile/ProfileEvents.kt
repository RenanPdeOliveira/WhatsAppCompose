package com.example.whatsappcompose.main.presentation.profile

sealed interface ProfileEvents {
    data class OnSaveButtonClick(val name: String, val photo: String): ProfileEvents
    data class OnSignOutButtonClick(val open: Boolean = true): ProfileEvents
    data object OnNavigateBackClick: ProfileEvents
    data object OnPhotoButtonClick: ProfileEvents
    data class PositiveDialogButton(val close: Boolean = false): ProfileEvents
    data class NegativeDialogButton(val close: Boolean = false): ProfileEvents
}