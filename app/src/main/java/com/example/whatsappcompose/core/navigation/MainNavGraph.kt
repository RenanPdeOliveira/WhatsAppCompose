package com.example.whatsappcompose.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.whatsappcompose.main.presentation.main.MainScreen
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
            MainScreen(
                onNavigate = {
                    navController.navigate(Screens.ProfileScreen)
                }
            )
        }
        composable<Screens.ProfileScreen> {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(
                popBackStack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.navigate(it.route)
                },
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent
            )
        }
    }
}