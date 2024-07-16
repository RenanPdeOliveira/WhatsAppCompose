package com.example.whatsappcompose.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.whatsappcompose.R
import com.example.whatsappcompose.ui.theme.DarkGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarNavigateBack(
    title: String,
    onNavigationBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButtonOnceClick(
                onClick = {
                    onNavigationBack()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = ""
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = DarkGreen,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White,
            scrolledContainerColor = Color.White,
            titleContentColor = Color.White
        ),
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarActionMenu(
    title: String,
    photo: String,
    onProfile: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        actions = {
            IconButtonOnceClick(
                onClick = {
                    onProfile()
                }
            ) {
                AsyncImage(
                    model = photo,
                    contentDescription = ""
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = DarkGreen,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White,
            scrolledContainerColor = Color.White,
            titleContentColor = Color.White
        ),
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarChat(
    title: String,
    photo: String? = null,
    onNavigationBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButtonOnceClick(
                onClick = {
                    onNavigationBack()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = ""
                )
            }
        },
        actions = {
            IconButtonOnceClick(
                onClick = {

                }
            ) {
                if (photo.isNullOrEmpty()) {
                    Image(
                        painter = painterResource(id = R.drawable.undraw_pic_profile_empty),
                        contentDescription = ""
                    )
                } else {
                    AsyncImage(
                        model = photo,
                        contentDescription = ""
                    )
                }
            }
        },
        colors = TopAppBarColors(
            containerColor = DarkGreen,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White,
            scrolledContainerColor = Color.White,
            titleContentColor = Color.White
        ),
        scrollBehavior = scrollBehavior
    )
}