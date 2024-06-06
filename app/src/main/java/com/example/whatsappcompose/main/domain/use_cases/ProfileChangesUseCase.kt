package com.example.whatsappcompose.main.domain.use_cases

import com.example.whatsappcompose.core.domain.Result
import com.example.whatsappcompose.core.domain.User
import com.example.whatsappcompose.main.domain.ProfileError
import com.example.whatsappcompose.main.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileChangesUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke(
        name: String,
        photo: String
    ): Flow<Result<User, ProfileError>> {
        if (name.isBlank()) {
            return flow { emit(Result.Error(error = ProfileError.Fields.NAME_EMPTY)) }
        }
        if (photo.isBlank()) {
            return flow { emit(Result.Error(error = ProfileError.Fields.PHOTO_EMPTY)) }
        }
        return repository.saveChanges(name, photo)
    }
}