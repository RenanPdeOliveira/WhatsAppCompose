package com.example.whatsappcompose.main.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.whatsappcompose.R
import com.example.whatsappcompose.core.presentation.components.TopAppBarChat
import com.example.whatsappcompose.core.util.UiEvent
import com.example.whatsappcompose.ui.theme.DarkGreen
import kotlinx.coroutines.flow.Flow

@Composable
fun ChatScreen(
    onNavigateBack: (UiEvent.PopBackStack) -> Unit,
    onEvent: (ChatEvents) -> Unit,
    uiEvent: Flow<UiEvent>,
    name: String,
    photo: String? = null
) {
    val snackBarHost = remember {
        SnackbarHostState()
    }
    var message by remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = true) {
        uiEvent.collect { uiEvent ->
            when (uiEvent) {
                UiEvent.PopBackStack -> {
                    onNavigateBack(UiEvent.PopBackStack)
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
            TopAppBarChat(
                title = name,
                photo = photo,
                onNavigationBack = {
                    onEvent(ChatEvents.OnNavigateBack)
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .paint(
                    painterResource(id = R.drawable.chat_background),
                    contentScale = ContentScale.FillBounds
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(100) {
                Text(text = "$it")
            }
        }
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (textField, iconButton) = createRefs()
            TextField(
                modifier = Modifier
                    .constrainAs(textField) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(iconButton.start)
                        width = Dimension.fillToConstraints
                    }
                    .height(60.dp)
                    .background(Color.White),
                value = message,
                onValueChange = {
                    message = it
                },
                placeholder = {
                    Text(text = "Message")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            IconButton(
                modifier = Modifier
                    .constrainAs(iconButton) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .height(60.dp)
                    .background(DarkGreen),
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.Send,
                    tint = Color.White,
                    contentDescription = ""
                )
            }
        }
    }
}