package com.cs3450.dansfrappesraps.ui.navigation

data class Screen(val route: String)

object Routes {
    val foyer = Screen("foyer")
    val signIn = Screen("signin")
    val signUp = Screen("signup")
    val menu = Screen("menu")
    val app = Screen("app")
    val splashScreen = Screen("splashscreen")
}
