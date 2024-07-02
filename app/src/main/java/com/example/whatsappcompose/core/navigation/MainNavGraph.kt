package com.example.whatsappcompose.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.whatsappcompose.main.presentation.chat.ChatScreen
import com.example.whatsappcompose.main.presentation.chat.ChatViewModel
import com.example.whatsappcompose.main.presentation.main.MainScreen
import com.example.whatsappcompose.main.presentation.main.MainViewModel
import com.example.whatsappcompose.main.presentation.profile.ProfileScreen
import com.example.whatsappcompose.main.presentation.profile.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.mainNavGraph(
    navController: NavController
) {
    navigation<Screens.Main>(
        startDestination = Screens.MainScreen
    ) {
        composable<Screens.MainScreen> {
            val viewModel = hiltViewModel<MainViewModel>()
            val state = viewModel.state.collectAsState()
            MainScreen(
                onNavigate = {
                    navController.navigate(it.route)
                },
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                state = state
            )
        }
        composable<Screens.ProfileScreen> {
            val args = it.toRoute<Screens.ProfileScreen>()
            val viewModel = hiltViewModel<ProfileViewModel>()
            val state = viewModel.state.collectAsState()
            ProfileScreen(
                onNavigate = { uiEvent ->
                    navController.navigate(uiEvent.route)
                },
                onNavigateBack = {
                    navController.navigateUp()
                },
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                state = state,
                nameState = args.name,
                photoState = args.photo
            )
        }
        composable<Screens.ChatScreen> {
            val args = it.toRoute<Screens.ChatScreen>()
            ChatScreen(
                name = args.name,
                photo = args.photo
            )
        }
    }
}