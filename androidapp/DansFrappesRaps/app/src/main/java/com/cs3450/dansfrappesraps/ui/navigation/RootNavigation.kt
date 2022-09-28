package com.cs3450.dansfrappesraps.ui.navigation

import android.view.Menu
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.cs3450.dansfrappesraps.ui.screens.MenuScreen
import com.cs3450.dansfrappesraps.ui.screens.SignInScreen
import com.cs3450.dansfrappesraps.ui.screens.SignUpScreen

@Composable
fun RootNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(){
                Text(text="Top App Bar!")
            }
        },
    ){
        NavHost(
            navController = navController,
            startDestination = Routes.foyer.route,
            modifier = Modifier.padding(paddingValues = it)
        ) {
            navigation(route = Routes.foyer.route, startDestination = Routes.signIn.route){
                composable(route = Routes.signIn.route) { SignInScreen(navHostController = navController)}
                composable(route = Routes.signUp.route) { SignUpScreen(navController)}
            }
            navigation(route = Routes.app.route, startDestination = Routes.menu.route) {
                composable(route = Routes.menu.route) {MenuScreen(navHostController = navController)}
            }
//            composable(route = Routes.signIn.route) { SignInScreen(navHostController = navController)}
//            composable(route = Routes.menu.route) {MenuScreen(navController)}
        }
    }
//    NavHost(navController = navController, startDestination = Routes.signIn.route) {
//        composable(Routes.signIn.route) { SignInScreen(navController = navController)}
//        composable(Routes.menu.route) { MenuScreen(navController = navController)}
//    }
}