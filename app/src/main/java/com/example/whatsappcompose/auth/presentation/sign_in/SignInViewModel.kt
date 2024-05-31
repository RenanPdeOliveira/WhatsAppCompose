package com.example.whatsappcompose.auth.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappcompose.R
import com.example.whatsappcompose.auth.domain.AuthError
import com.example.whatsappcompose.auth.domain.use_cases.AuthUseCase
import com.example.whatsappcompose.core.domain.Result
import com.example.whatsappcompose.core.navigation.Screens
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
class SignInViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SignInEvents) {
        when (event) {
            SignInEvents.OnResetPasswordClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Screens.ForgotPasswordScreen))
                }
            }

            SignInEvents.OnSignUpButtonClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Screens.SignUpScreen))
                }
            }

            is SignInEvents.OnSignInButtonClick -> {
                signIn(email = event.email, password = event.password)
            }
        }
    }

    private fun signIn(email: String, password: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        delay(1000L)
        when (val result = authUseCase.signInUseCase(email, password)) {
            is Result.Error -> when (result.error) {
                AuthError.SignIn.Exceptions.KOTLIN_EXCEPTION -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(uiText = UiText.StringResource(R.string.snackbar_unknown_error)))
                }

                AuthError.SignIn.Exceptions.WEAK_PASSWORD_EXCEPTION -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(uiText = UiText.StringResource(R.string.snackbar_weak_password)))
                }

                AuthError.SignIn.Exceptions.COLLISION_EXCEPTION -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(uiText = UiText.StringResource(R.string.snackbar_collision_email)))
                }

                AuthError.SignIn.Exceptions.CREDENTIALS_EXCEPTION -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(uiText = UiText.StringResource(R.string.snackbar_invalid_email)))
                }

                AuthError.SignIn.Fields.EMAIL_EMPTY -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(uiText = UiText.StringResource(R.string.snackbar_fill_in_email_field)))
                }

                AuthError.SignIn.Fields.PASSWORD_EMPTY -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(uiText = UiText.StringResource(R.string.snackbar_fill_in_password_field)))
                }

                AuthError.SignIn.Fields.EMAIL_PASSWORD_EMPTY -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.ShowSnackBar(uiText = UiText.StringResource(R.string.snackbar_fill_in_all_fields)))
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