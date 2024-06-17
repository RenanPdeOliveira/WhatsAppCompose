package com.example.whatsappcompose.main.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappcompose.R
import com.example.whatsappcompose.core.domain.util.Result
import com.example.whatsappcompose.core.domain.User
import com.example.whatsappcompose.core.navigation.Screens
import com.example.whatsappcompose.core.util.UiEvent
import com.example.whatsappcompose.core.util.UiText
import com.example.whatsappcompose.main.domain.util.ProfileError
import com.example.whatsappcompose.main.domain.use_cases.MainUseCases
import com.example.whatsappcompose.main.presentation.main.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val mainUseCases: MainUseCases
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(UserState())
    val state = _state.asStateFlow()

    fun onEvent(event: ProfileEvents) {
        when (event) {
            is ProfileEvents.OnSaveButtonClick -> {
                saveChanges(event.name, event.photo)
            }

            is ProfileEvents.OnSignOutButtonClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.ShowDialog(event.open))
                }
            }

            ProfileEvents.OnNavigateBackClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.PopBackStack)
                }
            }

            ProfileEvents.OnPhotoButtonClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.OpenGallery)
                }
            }

            is ProfileEvents.PositiveDialogButton -> {
                signOut(event.close)
            }

            is ProfileEvents.NegativeDialogButton -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.ShowDialog(event.close))
                }
            }
        }
    }

    private fun signOut(close: Boolean) = viewModelScope.launch {
        _uiEvent.send(UiEvent.ShowDialog(close))
        _state.update { it.copy(isLoading = true) }
        delay(1000L)
        when (val result = mainUseCases.signOutUseCase()) {
            is Result.Error -> when (result.error) {
                ProfileError.Exception.KOTLIN_EXCEPTION -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.snackbar_sign_out_error)))
                }

                else -> Unit
            }

            is Result.Success -> {
                _uiEvent.send(UiEvent.Navigate(Screens.Auth))
                delay(2000L)
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun saveChanges(name: String, photo: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        delay(1000L)
        mainUseCases.profileChangesUseCase(name, photo).collect { result ->
            when (result) {
                is Result.Error -> when (result.error) {
                    ProfileError.Exception.KOTLIN_EXCEPTION -> {
                        _state.update { it.copy(isLoading = false) }
                        _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.snackbar_save_changes_error)))
                    }

                    ProfileError.Fields.NAME_EMPTY -> {
                        _state.update { it.copy(isLoading = false) }
                        _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.snackbar_fill_in_name_field)))
                    }

                    ProfileError.Fields.PHOTO_EMPTY -> {
                        _state.update { it.copy(isLoading = false) }
                        _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.snackbar_photo_empty)))
                    }
                }

                is Result.Success -> {
                    _uiEvent.send(UiEvent.Navigate(Screens.MainScreen))
                    delay(1000L)
                    _state.update {
                        it.copy(
                            user = User(
                                name = result.data.name,
                                photo = result.data.photo
                            ),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}