package com.cs3450.dansfrappesraps.ui.navigation

import EditUserScreen
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.cs3450.dansfrappesraps.ui.screens.*
import com.cs3450.dansfrappesraps.ui.viewmodels.RootNavigationViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RootNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scaffoldState = rememberScaffoldState()
    val viewModel: RootNavigationViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState

    LaunchedEffect(currentDestination?.route) {
        if (viewModel.isUserLoggedIn()) {
            val user = async { viewModel.initialSetup() }
            user.await()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary) {
                if (currentDestination?.route == Routes.signUp.route || currentDestination?.route == Routes.detailMenu.route) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                } else if (currentDestination?.hierarchy?.none { it.route == Routes.sideBar.route } == false) {
                    IconButton(onClick = {
                        navController.popBackStack()
                        if (currentDestination.route != Routes.editUser.route && currentDestination.route != Routes.editInventory.route && currentDestination.route != Routes.editMenu.route) {
                            scope.launch {
                                delay(500)
                                scaffoldState.drawerState.open()
                            }
                        }

                    }) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                } else if (currentDestination?.hierarchy?.none { it.route == Routes.foyer.route || it.route == Routes.splashScreen.route } == true) {
                    IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                        Icon(Icons.Outlined.Menu, contentDescription = "Menu Button")
                    }
                } else {

                }
            }
        },
        drawerContent = {
            if (currentDestination?.hierarchy?.none { it.route == Routes.foyer.route || it.route == Routes.splashScreen.route } == true) {
                DropdownMenuItem(onClick = {
                    /*TODO*/
                }) {
                    Icon(Icons.Outlined.AccountCircle, "Profile")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Profile")
                }
                if (state.isEmployee || state.isManager) {
                    DropdownMenuItem(onClick = {
                        if (state.isEmployee) {
                            navController.navigate(Routes.managePayroll.route)
                        } else {
                            navController.navigate(Routes.employeePayroll.route)
                        }
                        scope.launch { scaffoldState.drawerState.close() }
                    }) {
                        Icon(Icons.Outlined.DateRange, "Hours")
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "Payroll")
                    }
                }
                if (state.isManager) {
                    DropdownMenuItem(onClick = {
                        navController.navigate(Routes.manageInventory.route)
                        scope.launch { scaffoldState.drawerState.close() }
                    }) {
                        Icon(Icons.Outlined.PendingActions, "Inventory")
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "Manage Inventory")
                    }
                    DropdownMenuItem(onClick = {
                        navController.navigate(Routes.manageMenu.route)
                        scope.launch { scaffoldState.drawerState.close() }
                    }) {
                        Icon(Icons.Outlined.Storefront, "Menu")
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "Manage Menu")
                    }
                    DropdownMenuItem(onClick = {
                        navController.navigate(Routes.manageUsers.route)
                        scope.launch { scaffoldState.drawerState.close() }
                    }) {
                        Icon(Icons.Outlined.ManageAccounts, "Manage Users")
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "Manage Users")
                    }
                }
                DropdownMenuItem(onClick = {
                    viewModel.signOutUser()
                    scope.launch {
                        scaffoldState.drawerState.snapTo(DrawerValue.Closed)
                    }
                    navController.navigate(Routes.foyer.route) {
                        popUpTo(0)
                    }
                }) {
                    Icon(Icons.Outlined.ExitToApp, "Logout")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Logout")
                }

            }
        },
        floatingActionButton = {
            if (currentDestination?.hierarchy?.none { it.route == Routes.menu.route } == false) {
                FloatingActionButton(
                    onClick = { /*TODO*/ },
                    contentColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Outlined.ShoppingCart, contentDescription = "Cart")
                }
            }
            if (currentDestination?.route == Routes.manageUsers.route || currentDestination?.route == Routes.manageInventory.route || currentDestination?.route == Routes.manageMenu.route) {
                FloatingActionButton(
                    onClick = {
                        if (currentDestination.route == Routes.manageUsers.route) {
                            navController.navigate(Routes.editUser.route)
                        } else if (currentDestination.route == Routes.manageInventory.route) {
                            navController.navigate(Routes.editInventory.route)
                        } else if (currentDestination.route == Routes.manageMenu.route) {
                            navController.navigate(Routes.editMenu.route)
                        }
                    },
                    contentColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Outlined.Add, contentDescription = "Add")
                }
            }
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.splashScreen.route,
            modifier = Modifier.padding(paddingValues = it)
        ) {
            navigation(route = Routes.foyer.route, startDestination = Routes.signIn.route) {
                composable(route = Routes.signIn.route) { SignInScreen(navHostController = navController) }
                composable(route = Routes.signUp.route) { SignUpScreen(navHostController = navController) }
            }
            navigation(route = Routes.app.route, startDestination = Routes.menu.route) {
                composable(route = Routes.menu.route) { MenuScreen(navHostController = navController) }
                composable(route = Routes.detailMenu.route,
                    arguments = listOf(navArgument("id") { defaultValue = "new" })
                ) { navBackStackEntry ->
                    DetailMenuScreen(navController, navBackStackEntry.arguments?.get("id").toString())
                }
            }
            //TODO make this default to profile screen
            navigation(route = Routes.sideBar.route, startDestination = Routes.manageMenu.route) {
                composable(route = Routes.manageMenu.route) { ManageMenuScreen(navHostController = navController) }
                composable(route = Routes.manageUsers.route) { ManageUsersScreen(navHostController = navController) }
                composable(route = Routes.managePayroll.route) { ManagePayRollScreen(navController = navController) }
                composable(route = Routes.employeePayroll.route) { EmployeePayrollScreen(navController = navController) }
                composable(
                    route = Routes.editUser.route,
                    arguments = listOf(navArgument("id") { defaultValue = "new" })
                ) { navBackStackEntry ->
                    EditUserScreen(navController, navBackStackEntry.arguments?.get("id").toString())
                }
                composable(route = Routes.manageInventory.route) {
                    ManageInventoryScreen(
                        navHostController = navController
                    )
                }
                composable(
                    route = Routes.editInventory.route,
                    arguments = listOf(navArgument("id") { defaultValue = "new" })
                ) { navBackStackEntry ->
                    AdjustInventoryScreen(navController, navBackStackEntry.arguments?.get("id").toString())
                }
                composable(
                    route = Routes.editMenu.route,
                    arguments = listOf(navArgument("id") { defaultValue = "new" })
                ) { navBackStackEntry ->
                    EditMenuScreen(navController, navBackStackEntry.arguments?.get("id").toString())
                }
            }
            composable(route = Routes.splashScreen.route) { SplashScreen(navHostController = navController) }
        }
    }
}
