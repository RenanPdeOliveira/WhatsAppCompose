package com.example.whatsappcompose.auth.presentation.sign_up

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
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import com.example.whatsappcompose.core.presentation.components.TopAppBarNavigateBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.whatsappcompose.R
import com.example.whatsappcompose.auth.presentation.components.LottieAuthLoading
import com.example.whatsappcompose.ui.theme.DarkGreen
import com.example.whatsappcompose.core.util.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    popBackStack: (UiEvent.PopBackStack) -> Unit,
    onEvent: (SignUpEvents) -> Unit,
    uiEvent: Flow<UiEvent>,
    state: State<SignUpState>
) {
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPasswordHidden by remember {
        mutableStateOf(true)
    }
    val visualTransformation =
        if (isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None
    val isVisibly =
        if (isPasswordHidden) R.drawable.baseline_visibility_off else R.drawable.baseline_visibility_on
    val snackBarHost = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(UiEvent.Navigate(event.route))
                }

                UiEvent.PopBackStack -> {
                    popBackStack(UiEvent.PopBackStack)
                }

                is UiEvent.ShowSnackBar -> {
                    scope.launch {
                        snackBarHost.showSnackbar(message = event.uiText.asString(context))
                    }
                }
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
                title = stringResource(id = R.string.signup_toolbar_title),
                onNavigationBack = {
                    onEvent(SignUpEvents.OnNavigateBackClick)
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
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = {
                        Text(text = stringResource(id = R.string.name_text_input))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            tint = DarkGreen,
                            contentDescription = ""
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
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
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = {
                        Text(text = stringResource(id = R.string.password_text_input))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Lock,
                            tint = DarkGreen,
                            contentDescription = ""
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                isPasswordHidden = !isPasswordHidden
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = isVisibly),
                                contentDescription = ""
                            )
                        }
                    },
                    visualTransformation = visualTransformation
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        onEvent(SignUpEvents.OnSignUpButtonClick(name, email, password))
                    }
                ) {
                    Text(text = stringResource(id = R.string.create_account_button))
                }
            }
        } else {
            LottieAuthLoading()
        }
    }
}