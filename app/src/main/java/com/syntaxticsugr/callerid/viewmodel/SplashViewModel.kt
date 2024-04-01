package com.syntaxticsugr.callerid.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntaxticsugr.callerid.datastore.DataStorePref
import com.syntaxticsugr.callerid.enums.PermissionsResult
import com.syntaxticsugr.callerid.navigation.Screens
import com.syntaxticsugr.callerid.utils.PermissionsManager
import com.syntaxticsugr.tcaller.utils.AuthKeyManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val pref: DataStorePref
) : ViewModel() {

    var isLoading by mutableStateOf(true)
    var startDestination by mutableStateOf(Screens.Welcome.route)

    private suspend fun removeSplash() {
        delay(500)
        isLoading = false
    }

    private suspend fun setStartDestination(context: Context) {
        val showWelcomePage = pref.readBool(key = "showWelcomePage", default = true)
        val arePermissionsGranted = PermissionsManager.arePermissionsGranted(context)
        val authKey = AuthKeyManager.getAuthKey(context)

        startDestination = if (showWelcomePage) {
            Screens.Welcome.route
        } else if (arePermissionsGranted != PermissionsResult.ALL_GRANTED) {
            Screens.Permissions.route
        } else if (authKey == null) {
            Screens.LogIn.route
        } else {
            Screens.Home.route
        }
    }

    fun setStartDestinationAndRemoveSplash(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            setStartDestination(context = context)
            removeSplash()
        }
    }

}
