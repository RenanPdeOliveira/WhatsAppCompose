package com.example.whatsappcompose.main.presentation.profile

sealed interface ProfileEvents {
    data class OnSaveButtonClick(val name: String, val photo: String): ProfileEvents
    data object OnSignOutButtonClick: ProfileEvents
    data object OnNavigateBackClick: ProfileEvents
    data object OnPhotoButtonClick: ProfileEvents
}