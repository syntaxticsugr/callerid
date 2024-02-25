package com.syntaxticsugr.callerid.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.syntaxticsugr.callerid.ui.screens.LoginScreen
import com.syntaxticsugr.callerid.ui.screens.WelcomeScreen

@ExperimentalAnimationApi
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = Screens.Welcome.route) {
            WelcomeScreen(navController = navController)
        }

        composable(route = Screens.Login.route) {
            LoginScreen(navController = navController)
        }

    }
}
