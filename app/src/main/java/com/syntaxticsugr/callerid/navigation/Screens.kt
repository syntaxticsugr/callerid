package com.syntaxticsugr.callerid.navigation

sealed class Screens(val route: String) {

    object Welcome : Screens(route = "welcome_screen")
    object Permission : Screens(route = "permissions_screen")
    object Login : Screens(route = "login_screen")
    object Home : Screens(route = "home_screen")

}
