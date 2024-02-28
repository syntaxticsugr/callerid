package com.syntaxticsugr.callerid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.syntaxticsugr.callerid.datastore.DataStorePref
import com.syntaxticsugr.callerid.navigation.Screens
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

        navController.navigate(Screens.Permissions.route) {
            popUpTo(Screens.Permissions.route) {
                inclusive = true
            }
        }
    }

}
