package com.example.whatsappcompose.auth.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappcompose.auth.domain.AuthError
import com.example.whatsappcompose.auth.domain.use_cases.AuthUseCase
import com.example.whatsappcompose.core.domain.Result
import com.example.whatsappcompose.core.navigation.Screens
import com.example.whatsappcompose.core.util.UiEvent
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
class SignUpViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
): ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SignUpEvents) {
        when (event) {
            SignUpEvents.OnNavigateBackClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.PopBackStack)
                }
            }
            is SignUpEvents.OnSignUpButtonClick -> {
                signUp(name = event.name, email = event.email, password = event.password)
            }
        }
    }

    private fun signUp(name: String, email: String, password: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        delay(1000L)
        when (val result = authUseCase.signUpUseCase(name, email, password)) {
            is Result.Error -> when (result.error) {
                AuthError.SignUp.Exceptions.KOTLIN_EXCEPTION -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "An unknown error occurred"))
                }
                AuthError.SignUp.Exceptions.WEAK_PASSWORD_EXCEPTION -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "Weak password, try another one"))
                }
                AuthError.SignUp.Exceptions.COLLISION_EXCEPTION -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "Email belongs to another user"))
                }
                AuthError.SignUp.Exceptions.CREDENTIALS_EXCEPTION -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "Invalid email, try another one"))
                }
                AuthError.SignUp.Fields.NAME_EMPTY -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "Fill in name field"))
                }
                AuthError.SignUp.Fields.EMAIL_EMPTY -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "Fill in email field"))
                }
                AuthError.SignUp.Fields.PASSWORD_EMPTY -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "Fill in password field"))
                }
                AuthError.SignUp.Fields.NAME_EMAIL_PASSWORD_EMPTY -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "Fill in all field"))
                }
            }
            is Result.Success -> {
                _uiEvent.send(UiEvent.Navigate(Screens.Main))
                delay(2000L)
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}