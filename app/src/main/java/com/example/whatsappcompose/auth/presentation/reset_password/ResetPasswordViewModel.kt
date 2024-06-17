package com.example.whatsappcompose.auth.presentation.reset_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappcompose.R
import com.example.whatsappcompose.auth.domain.util.AuthError
import com.example.whatsappcompose.auth.domain.use_cases.ResetPasswordUseCase
import com.example.whatsappcompose.core.domain.util.Result
import com.example.whatsappcompose.core.util.UiEvent
import com.example.whatsappcompose.core.util.UiText
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
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(ResetPasswordState())
    val state = _state.asStateFlow()

    fun onEvent(event: ResetPasswordEvents) {
        when (event) {
            ResetPasswordEvents.OnNavigateBackClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.PopBackStack)
                }
            }

            is ResetPasswordEvents.OnResetButtonClick -> {
                resetPassword(event.email)
            }
        }
    }

    private fun resetPassword(email: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        delay(1000L)
        when (val result = resetPasswordUseCase(email)) {
            is Result.Error -> when (result.error) {
                AuthError.ResetPassword.Exceptions.KOTLIN_EXCEPTION -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.snackbar_unknown_error)))
                }

                AuthError.ResetPassword.Fields.EMAIL_EMPTY -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.snackbar_fill_in_email_field)))
                }
            }

            is Result.Success -> {
                _state.update { it.copy(isLoading = false) }
                _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.snackbar_check_email)))
            }
        }
    }
}