package com.syntaxticsugr.callerid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.syntaxticsugr.callerid.datastore.DataStorePref
import com.syntaxticsugr.callerid.navigation.Screens
import com.syntaxticsugr.callerid.utils.navigateAndClean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val pref: DataStorePref
) : ViewModel() {

    private fun doNotShowWelcomePage() {
        viewModelScope.launch(Dispatchers.IO) {
            pref.writeBool(key = "showWelcomePage", value = false)
        }
    }

    fun nextScreen(navController: NavController) {
        doNotShowWelcomePage()

        navController.navigateAndClean(Screens.Permissions.route)
    }

}
