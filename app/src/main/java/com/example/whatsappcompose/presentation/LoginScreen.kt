package com.example.whatsappcompose.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappcompose.R
import com.example.whatsappcompose.navigation.Screens
import com.example.whatsappcompose.ui.theme.DarkGreen
import com.example.whatsappcompose.util.UiEvent

@Composable
fun LoginScreen(
    onEvent: (UiEvent.Navigate) -> Unit
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPasswordHidden by rememberSaveable {
        mutableStateOf(true)
    }
    val visualTransformation =
        if (isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None
    val isVisibly =
        if (isPasswordHidden) R.drawable.baseline_visibility_off else R.drawable.baseline_visibility_on

    Scaffold(

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_green),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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
                            tint = DarkGreen,
                            contentDescription = ""
                        )
                    }
                },
                visualTransformation = visualTransformation
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = {

                }
            ) {
                Text(text = stringResource(id = R.string.forgot_password_button))
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {

                }
            ) {
                Text(
                    text = stringResource(id = R.string.login_button),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    onEvent(UiEvent.Navigate(Screens.SignUpScreen))
                }
            ) {
                Text(
                    text = stringResource(id = R.string.create_account_button),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}