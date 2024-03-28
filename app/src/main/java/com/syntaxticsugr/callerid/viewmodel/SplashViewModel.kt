package com.syntaxticsugr.callerid.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
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

    val isLoading = mutableStateOf(true)
    val startDestination = mutableStateOf(Screens.Welcome.route)

    private fun removeSplash() {
        viewModelScope.launch {
            delay(500)
            isLoading.value = false
        }
    }

    fun setStartDestination(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val showWelcomePage = pref.readBool(key = "showWelcomePage", default = true)
            val arePermissionsGranted = PermissionsManager.arePermissionsGranted(context)
            val authKey = AuthKeyManager.getAuthKey(context)

            startDestination.value = if (showWelcomePage) {
                Screens.Welcome.route
            } else if (arePermissionsGranted != PermissionsResult.ALL_GRANTED) {
                Screens.Permissions.route
            } else if (authKey == null) {
                Screens.LogIn.route
            } else {
                Screens.Home.route
            }

            removeSplash()
        }
    }

}
