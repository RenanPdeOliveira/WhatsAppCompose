package com.example.whatsappcompose.main.presentation.profile

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.whatsappcompose.R
import com.example.whatsappcompose.core.presentation.components.TopAppBarNavigateBack
import com.example.whatsappcompose.ui.theme.DarkGreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileScreen(
    popBackStack: () -> Unit,
    onNavigate: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    var name by remember {
        mutableStateOf("")
    }
    var photo by remember {
        mutableStateOf("https://avatars.githubusercontent.com/u/14994036?v=4")
    }
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(photo)
            .size(200)
            .build()
    )
    val snackBarHost = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    val galleryPermission =
        rememberPermissionState(permission = Manifest.permission.READ_MEDIA_IMAGES)
    val permissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                scope.launch {
                    snackBarHost.showSnackbar("Permissao aprovada")
                }
            } else {
                scope.launch {
                    snackBarHost.showSnackbar("Permissao negada")
                }
            }
        }
    )
    val gallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                photo = uri.toString()
            } else {
                scope.launch {
                    snackBarHost.showSnackbar("Nenhuma imagem selecionada")
                }
            }
        }
    )
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHost)
        },
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
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape),
                    painter = painter,
                    contentDescription = ""
                )
                IconButton(
                    modifier = Modifier
                        .offset(70.dp, 70.dp)
                        .clip(CircleShape)
                        .background(Color.Black),
                    onClick = {
                        if (!galleryPermission.status.isGranted) {
                            permissionResultLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        } else {
                            gallery.launch("image/*")
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        tint = DarkGreen,
                        contentDescription = ""
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
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