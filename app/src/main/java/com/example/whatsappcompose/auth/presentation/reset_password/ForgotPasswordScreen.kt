package com.example.whatsappcompose.auth.presentation.reset_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappcompose.R
import com.example.whatsappcompose.core.presentation.components.ButtonOnceClick
import com.example.whatsappcompose.core.presentation.components.LottieAuthLoading
import com.example.whatsappcompose.core.presentation.components.TopAppBarNavigateBack
import com.example.whatsappcompose.ui.theme.DarkGreen
import com.example.whatsappcompose.core.util.UiEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun ForgotPasswordScreen(
    onNavigateBack: (UiEvent.PopBackStack) -> Unit,
    onEvent: (ResetPasswordEvents) -> Unit,
    uiEvent: Flow<UiEvent>,
    state: State<ResetPasswordState>
) {
    var email by remember {
        mutableStateOf("")
    }
    val snackBarHost = remember {
        SnackbarHostState()
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                UiEvent.PopBackStack -> {
                    onNavigateBack(UiEvent.PopBackStack)
                }

                is UiEvent.ShowSnackBar -> {
                    snackBarHost.showSnackbar(event.uiText.asString(context))
                }

                else -> Unit
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHost)
        },
        topBar = {
            TopAppBarNavigateBack(
                title = stringResource(id = R.string.forgot_password_toolbar_title),
                onNavigationBack = {
                    onEvent(ResetPasswordEvents.OnNavigateBackClick)
                }
            )
        }
    ) { innerPadding ->
        if (!state.value.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(150.dp),
                    painter = painterResource(id = R.drawable.logo_green),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.forgot_password_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = {
                        Text(text = stringResource(id = R.string.email_text_input))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Email,
                            tint = DarkGreen,
                            contentDescription = ""
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.forgot_password_instructions),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                ButtonOnceClick(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        onEvent(ResetPasswordEvents.OnResetButtonClick(email))
                    }
                ) {
                    Text(text = stringResource(id = R.string.reset_password_button))
                }
            }
        } else {
            LottieAuthLoading()
        }
    }
}