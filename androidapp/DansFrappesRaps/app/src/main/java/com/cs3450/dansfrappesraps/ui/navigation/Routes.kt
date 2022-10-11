package com.cs3450.dansfrappesraps.ui.navigation

data class Screen(val route: String)

object Routes {
    val foyer = Screen("foyer")
    val signIn = Screen("signin")
    val signUp = Screen("signup")
    val menu = Screen("menu")
    val app = Screen("app")
    val splashScreen = Screen("splashscreen")
    val sideBar = Screen("sideBar")
    val manageOrders = Screen("manageOrders")
    val manageUsers = Screen("manageUsers")
    val createNewUser = Screen("createNewUser")
}
