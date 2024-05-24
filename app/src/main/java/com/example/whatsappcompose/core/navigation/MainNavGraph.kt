package com.example.whatsappcompose.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.whatsappcompose.main.presentation.MainScreen

fun NavGraphBuilder.mainNavGraph(

) {
    navigation<Screens.Main>(
        startDestination = Screens.MainScreen
    ) {
        composable<Screens.MainScreen> {
            MainScreen()
        }
    }
}