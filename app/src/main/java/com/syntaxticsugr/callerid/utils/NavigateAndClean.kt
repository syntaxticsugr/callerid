package com.syntaxticsugr.callerid.utils

import androidx.navigation.NavController

fun NavController.navigateAndClean(route: String) {
    navigate(route = route) {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}
