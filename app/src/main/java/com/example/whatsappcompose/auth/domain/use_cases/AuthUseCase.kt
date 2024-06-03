package com.example.whatsappcompose.auth.domain.use_cases

import javax.inject.Inject

data class AuthUseCase @Inject constructor(
    val signInUseCase: SignInUseCase,
    val signUpUseCase: SignUpUseCase,
    val resetPasswordUseCase: ResetPasswordUseCase
)