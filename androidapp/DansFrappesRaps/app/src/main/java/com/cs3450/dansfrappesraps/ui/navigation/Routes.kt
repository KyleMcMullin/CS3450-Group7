package com.cs3450.dansfrappesraps.ui.navigation

data class Screen(val route: String)

object Routes {
    val foyer = Screen("foyer")
    val signIn = Screen("signin")
    val menu = Screen("menu")
    val app = Screen("app")
}