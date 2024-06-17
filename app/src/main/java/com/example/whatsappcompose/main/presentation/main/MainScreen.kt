package com.example.whatsappcompose.main.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.whatsappcompose.core.presentation.components.LottieAuthLoading
import com.example.whatsappcompose.core.presentation.components.TopAppBarActionMenu
import com.example.whatsappcompose.core.util.UiEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun MainScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    onEvent: (MainEvents) -> Unit,
    uiEvent: Flow<UiEvent>,
    state: State<UserState>
) {
    val snackBarHost = remember {
        SnackbarHostState()
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(UiEvent.Navigate(event.route))
                }

                is UiEvent.ShowSnackBar -> {
                    snackBarHost.showSnackbar(event.uiText.asString(context))
                }

                else -> Unit
            }
        }
    }
    if (!state.value.isLoading) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            snackbarHost = {
                SnackbarHost(hostState = snackBarHost)
            },
            topBar = {
                TopAppBarActionMenu(
                    title = "Main Screen",
                    photo = state.value.user.photo,
                    onProfile = {
                        onEvent(
                            MainEvents.OnProfileButtonClick(
                                name = state.value.user.name,
                                photo = state.value.user.photo
                            )
                        )
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

            }
        }
    } else {
        LottieAuthLoading()
    }
}