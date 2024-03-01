package com.syntaxticsugr.callerid.viewmodel

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntaxticsugr.callerid.datastore.DataStorePref
import com.syntaxticsugr.callerid.navigation.Screens
import com.syntaxticsugr.callerid.permissions.requiredPermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class SplashViewModel(
    application: Application,
    private val pref: DataStorePref
) : ViewModel() {

    private val appContext: Context = application.applicationContext
    private val asd = appContext.filesDir
    private val authfile = "auth.key"

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Screens.Welcome.route)
    val startDestination: State<String> = _startDestination

    private fun removeSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            _isLoading.value = false
        }, 500)
    }

    private fun setStartDestination() {
        viewModelScope.launch(Dispatchers.IO) {
            val showWelcomePage = pref.readBool(key = "showWelcomePage", default = true)

            if (showWelcomePage) {
                _startDestination.value = Screens.Welcome.route
            } else {
                val authKey = File(asd, authfile)

                if (authKey.exists()) {
                    _startDestination.value = Screens.Home.route
                } else {
                    _startDestination.value = Screens.LogIn.route
                }

                for (permission in requiredPermissions) {
                    if (appContext.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                        _startDestination.value = Screens.Permissions.route
                        break
                    }
                }
            }

            removeSplash()
        }
    }

    private fun setStartDestinationAndRemoveSplash() {
        setStartDestination()
    }

    init {
        setStartDestinationAndRemoveSplash()
    }
}
