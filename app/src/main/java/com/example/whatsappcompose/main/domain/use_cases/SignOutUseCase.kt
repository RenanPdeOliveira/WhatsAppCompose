package com.example.whatsappcompose.main.domain.use_cases

import com.example.whatsappcompose.main.domain.util.ProfileError
import com.example.whatsappcompose.main.domain.repository.ProfileRepository
import com.example.whatsappcompose.core.domain.util.Result
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Result<Unit, ProfileError> {
        return repository.signOut()
    }
}