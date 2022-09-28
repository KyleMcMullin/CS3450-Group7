package com.cs3450.dansfrappesraps.ui.navigation

data class Screen(val route: String)

object Routes {
    val foyer = Screen("foyer")
    val signIn = Screen("signin")
<<<<<<< HEAD
=======
    val signUp = Screen("signup")
>>>>>>> 4192c327d562c1d1aa9a52311c372058305cff5f
    val menu = Screen("menu")
    val app = Screen("app")
}