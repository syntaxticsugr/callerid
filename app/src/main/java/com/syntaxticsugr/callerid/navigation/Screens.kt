package com.syntaxticsugr.callerid.navigation

sealed class Screens(val route: String) {
    data object Welcome : Screens(route = "welcome_screen")
    data object Permissions : Screens(route = "permissions_screen")
    data object LogIn : Screens(route = "login_screen")
    data object Verify : Screens(route = "verify_screen")
    data object Home : Screens(route = "home_screen")
    data object History : Screens(route = "history_screen")
}
