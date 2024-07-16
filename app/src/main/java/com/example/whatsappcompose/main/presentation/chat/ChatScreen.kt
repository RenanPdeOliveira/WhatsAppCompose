package com.example.whatsappcompose.main.presentation.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.whatsappcompose.R
import com.example.whatsappcompose.core.presentation.components.TopAppBarChat

@Composable
fun ChatScreen(
    name: String,
    photo: String? = null
) {
    val snackBarHost = remember {
        SnackbarHostState()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHost)
        },
        topBar = {
            TopAppBarChat(
                title = name,
                photo = photo,
                onNavigationBack = {

                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .paint(painterResource(id = R.drawable.chat_background), contentScale = ContentScale.FillBounds)
        ) {

        }
    }
}