package com.example.whatsappcompose.core.domain

data class User(
    val id: String,
    val name: String,
    val email: String,
    val photo: String = ""
)