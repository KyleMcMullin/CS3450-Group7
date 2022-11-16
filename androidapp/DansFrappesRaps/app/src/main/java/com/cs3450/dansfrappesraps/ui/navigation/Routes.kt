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
    val manageMenu = Screen("manageMenu")
    val editMenu = Screen("editMenu?id={id}")
    val detailMenu = Screen("detailMenu?id={id}")
    val manageUsers = Screen("manageUsers")
    val editUser = Screen("editUser?id={id}")
    val manageInventory = Screen("manageInventory")
    val editInventory = Screen("editInventory?id={id}&index={index}")
    val cart = Screen("cart")
    val managePayroll = Screen("managePayroll")
    val employeePayroll = Screen("employeePayroll")
    val profile = Screen("profile")
    val editProfile = Screen("editProfile")
    val tracker = Screen("tracker")
    val trackerEmployee = Screen("trackerEmployee")
}
