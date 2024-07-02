package com.example.whatsappcompose.main.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappcompose.core.navigation.Screens
import com.example.whatsappcompose.core.util.UiEvent
import com.example.whatsappcompose.main.presentation.main.MainEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(

): ViewModel() {

   /* private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ChatEvents) {
        when (event) {
            is ChatEvents.OnContactClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Screens.ProfileScreen(event.name, event.photo)))
                }
            }
        }
    }*/
}