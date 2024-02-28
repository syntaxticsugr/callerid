package com.syntaxticsugr.callerid.navigation

sealed class Screens(val route: String) {

    object Welcome : Screens(route = "welcome_screen")
    object Permissions : Screens(route = "permissions_screen")
    object LogIn : Screens(route = "login_screen")
    object Verify : Screens(route = "verify_screen")
    object Home : Screens(route = "home_screen")

}
