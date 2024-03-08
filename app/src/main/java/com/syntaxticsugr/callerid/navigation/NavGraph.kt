package com.syntaxticsugr.callerid.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.syntaxticsugr.callerid.ui.screens.HistoryScreen
import com.syntaxticsugr.callerid.ui.screens.HomeScreen
import com.syntaxticsugr.callerid.ui.screens.LogInScreen
import com.syntaxticsugr.callerid.ui.screens.PermissionsScreen
import com.syntaxticsugr.callerid.ui.screens.VerifyScreen
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

        composable(route = Screens.Permissions.route) {
            PermissionsScreen(navController = navController)
        }

        composable(route = Screens.LogIn.route) {
            LogInScreen(navController = navController)
        }

        composable(route = Screens.Verify.route) {
            VerifyScreen(navController = navController)
        }

        composable(route = Screens.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(
            route = "${Screens.History.route}/{phoneNumber}",
            arguments = listOf(navArgument("phoneNumber") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val phoneNumber = navBackStackEntry.arguments?.getString("phoneNumber")!!
            HistoryScreen(navController = navController, phoneNumber = phoneNumber)
        }

    }

}
