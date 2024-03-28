package com.syntaxticsugr.callerid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class HomeViewModel : ViewModel() {
    var searchNumber = ""
    var searchNumberError by mutableStateOf(false)

    fun openProfilePage(navController: NavController) {
        /* TODO */
    }

}
