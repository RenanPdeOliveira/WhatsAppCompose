package com.example.whatsappcompose.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.whatsappcompose.main.presentation.MainScreen
import com.example.whatsappcompose.main.presentation.profile.ProfileScreen

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
            ProfileScreen(
                popBackStack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.navigate(Screens.Auth)
                }
            )
        }
    }
}