package com.example.whatsappcompose.main.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.whatsappcompose.R
import com.example.whatsappcompose.core.presentation.components.TopAppBarNavigateBack
import com.example.whatsappcompose.ui.theme.DarkGreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(
    popBackStack: () -> Unit,
    onNavigate: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPasswordHidden by remember {
        mutableStateOf(false)
    }
    val visualTransformation =
        if (isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None
    val isVisibly =
        if (isPasswordHidden) R.drawable.baseline_visibility_off else R.drawable.baseline_visibility_on
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBarNavigateBack(
                title = stringResource(id = R.string.profile_toolbar_title),
                onNavigationBack = {
                    popBackStack()
                }
            )
        }
    ) { innerPadding ->
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
                    .size(200.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = ""
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
                            tint = DarkGreen,
                            contentDescription = ""
                        )
                    }
                },
                visualTransformation = visualTransformation
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    TODO()
                }
            ) {
                Text(text = stringResource(id = R.string.save_button))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    auth.signOut()
                    onNavigate()
                }
            ) {
                Text(text = stringResource(id = R.string.sign_out_button))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}