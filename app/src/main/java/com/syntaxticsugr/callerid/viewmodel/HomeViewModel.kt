package com.syntaxticsugr.callerid.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class HomeViewModel(
    application: Application,
) : ViewModel() {

    private val appContext: Context = application.applicationContext

    fun openSettings(navController: NavController) {
        /* TODO */
    }

}
