package com.cs3450.dansfrappesraps.ui.navigation

import android.view.Menu
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.cs3450.dansfrappesraps.ui.screens.MenuScreen
import com.cs3450.dansfrappesraps.ui.screens.SignInScreen
import com.cs3450.dansfrappesraps.ui.screens.SignUpScreen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
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
            TopAppBar() {
                if (currentDestination?.route == Routes.signUp.route) {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                } else {
                    IconButton(onClick= {scope.launch { scaffoldState.drawerState.open()}}) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu Button")
                    }
                }

            }
        },
        drawerContent = {
            if (currentDestination?.hierarchy?.none { it.route == Routes.foyer.route  } == true) {
                DropdownMenuItem(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.snapTo(DrawerValue.Closed)
                    }
                    navController.navigate(Routes.foyer.route) {
                        popUpTo(0)
                    }
                }) {
                    Icon(Icons.Default.ExitToApp, "Logout")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Logout")
                }
            }
        },
        floatingActionButton = {},
    ){
        NavHost(
            navController = navController,
            startDestination = Routes.foyer.route,
            modifier = Modifier.padding(paddingValues = it)
        ) {
           navigation(route = Routes.foyer.route, startDestination = Routes.signIn.route){
               composable(route = Routes.signIn.route) { SignInScreen(navHostController = navController)}
               composable(route = Routes.signUp.route) { SignUpScreen(navHostController = navController)}
           }
           navigation(route = Routes.app.route, startDestination = Routes.menu.route) {
               composable(route = Routes.menu.route) {MenuScreen(navHostController = navController)}
           }
        }
    }

}
