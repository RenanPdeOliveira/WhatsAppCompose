package com.example.whatsappcompose.main.presentation.main

sealed interface MainEvents {
    data class OnProfileButtonClick(val name: String, val photo: String): MainEvents
}