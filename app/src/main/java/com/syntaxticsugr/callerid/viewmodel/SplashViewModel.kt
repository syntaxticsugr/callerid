package com.syntaxticsugr.callerid.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntaxticsugr.callerid.datastore.DataStorePref
import com.syntaxticsugr.callerid.enums.PermissionsResult
import com.syntaxticsugr.callerid.navigation.Screens
import com.syntaxticsugr.callerid.utils.AuthKeyManager
import com.syntaxticsugr.callerid.utils.PermissionsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    application: Application,
    private val pref: DataStorePref
) : ViewModel() {

    private val appContext: Context = application.applicationContext

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Screens.Welcome.route)
    val startDestination: State<String> = _startDestination

    private fun removeSplash() {
        viewModelScope.launch {
            delay(500)
            _isLoading.value = false
        }
    }

    private fun checkPermissions() {
        if (PermissionsManager.arePermissionsGranted(appContext) != PermissionsResult.ALL_GRANTED) {
            _startDestination.value = Screens.Permissions.route
        }
    }

    private fun setStartDestinationAndRemoveSplash() {
        viewModelScope.launch(Dispatchers.IO) {
            val showWelcomePage = pref.readBool(key = "showWelcomePage", default = true)

            if (showWelcomePage) {
                _startDestination.value = Screens.Welcome.route
            } else {
                if (AuthKeyManager.getAuthKey(appContext) != null) {
                    _startDestination.value = Screens.Home.route
                } else {
                    _startDestination.value = Screens.LogIn.route
                }

                checkPermissions()
            }

            removeSplash()
        }
    }

    init {
        setStartDestinationAndRemoveSplash()
    }

}
