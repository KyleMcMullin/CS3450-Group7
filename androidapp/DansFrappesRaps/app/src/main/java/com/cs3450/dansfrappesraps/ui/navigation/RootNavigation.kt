package com.cs3450.dansfrappesraps.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cs3450.dansfrappesraps.ui.screens.MenuScreen
import com.cs3450.dansfrappesraps.ui.screens.SignInScreen

@Composable
fun RootNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.signIn.route) {
        composable(Routes.signIn.route) { SignInScreen(navController = navController)}
        composable(Routes.menu.route) { MenuScreen(navController = navController)}
    }
}