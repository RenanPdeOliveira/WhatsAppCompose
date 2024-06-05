package com.example.whatsappcompose.main.domain.use_cases

import com.example.whatsappcompose.core.domain.Result
import com.example.whatsappcompose.main.domain.ProfileError
import com.example.whatsappcompose.main.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileChangesUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(
        name: String,
        photo: String
    ): Result<Unit, ProfileError> {
        if (name.isEmpty()) {
            return Result.Error(error = ProfileError.Fields.NAME_EMPTY)
        }
        if (photo.isEmpty()) {
            return Result.Error(error = ProfileError.Fields.PHOTO_EMPTY)
        }
        return repository.saveChanges(name, photo)
    }
}