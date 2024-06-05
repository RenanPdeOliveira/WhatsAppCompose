package com.example.whatsappcompose.main.domain.use_cases

import javax.inject.Inject

data class MainUseCases @Inject constructor(
    val signOutUseCase: SignOutUseCase,
    val profileChangesUseCase: ProfileChangesUseCase
)