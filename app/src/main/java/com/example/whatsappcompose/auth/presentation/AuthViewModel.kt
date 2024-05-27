package com.example.whatsappcompose.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappcompose.auth.domain.AuthError
import com.example.whatsappcompose.auth.domain.use_cases.AuthUseCase
import com.example.whatsappcompose.core.domain.Result
import com.example.whatsappcompose.core.navigation.Screens
import com.example.whatsappcompose.core.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun signIn(email: String, password: String) = viewModelScope.launch {
        _state.update {
            it.copy(isLoading = true)
        }
        delay(2000L)
        when (val result = authUseCase.loginUseCase(email, password)) {
            is Result.Error -> when (result.error) {
                AuthError.Exceptions.KOTLIN_EXCEPTION -> {
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "An unknown error occurred"))
                }
                AuthError.Exceptions.WEAK_PASSWORD_EXCEPTION -> {
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "Weak password, try another one"))
                }
                AuthError.Exceptions.COLLISION_EXCEPTION -> {
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "Email belongs to another user"))
                }
                AuthError.Exceptions.CREDENTIALS_EXCEPTION -> {
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "Invalid email, try another one"))
                }
                AuthError.Fields.EMAIL_EMPTY -> {
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "Fill in email field"))
                }
                AuthError.Fields.PASSWORD_EMPTY -> {
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "Fill in password field"))
                }
                AuthError.Fields.EMAIL_PASSWORD_EMPTY -> {
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "Fill in all fields"))
                }
            }
            is Result.Success -> {
                _uiEvent.send(UiEvent.Navigate(Screens.MainScreen))
                _state.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }
}