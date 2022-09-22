package com.cs3450.dansfrappesraps.ui.navigation

data class Screen(val route: String)

object Routes {
    val signIn = Screen("signin")
    val menu = Screen("menu")
}