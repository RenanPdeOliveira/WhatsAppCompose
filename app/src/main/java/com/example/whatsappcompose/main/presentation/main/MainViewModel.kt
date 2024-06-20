package com.example.whatsappcompose.main.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappcompose.R
import com.example.whatsappcompose.core.domain.util.Result
import com.example.whatsappcompose.core.domain.User
import com.example.whatsappcompose.core.navigation.Screens
import com.example.whatsappcompose.core.util.UiEvent
import com.example.whatsappcompose.core.util.UiText
import com.example.whatsappcompose.main.domain.util.MainError
import com.example.whatsappcompose.main.domain.repository.MainRepository
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
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(UserState())
    val state = _state.asStateFlow()

    init {
        _state.update { it.copy(isLoading = true) }
        getUserData()
        getUsers()
    }

    fun onEvent(event: MainEvents) {
        when (event) {
            is MainEvents.OnProfileButtonClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Screens.ProfileScreen(event.name, event.photo)))
                }
            }
        }
    }

    private fun getUserData() = viewModelScope.launch {
        repository.getUserData().collect { result ->
            when (result) {
                is Result.Error -> when (result.error) {
                    MainError.Exception.KOTLIN_EXCEPTION -> {
                        _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.snackbar_user_data_error)))
                    }
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            user = User(
                                name = result.data.name,
                                photo = result.data.photo
                            )
                        )
                    }
                }
            }
        }
    }

    private fun getUsers() = viewModelScope.launch {
        repository.getUsers().collect { result ->
            when (result) {
                is Result.Error -> when (result.error) {
                    MainError.Exception.KOTLIN_EXCEPTION -> {
                        _state.update { it.copy(isLoading = false) }
                        _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.snackbar_user_data_error)))
                    }
                }

                is Result.Success -> {
                    delay(2000L)
                    _state.update {
                        it.copy(
                            users = result.data,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}