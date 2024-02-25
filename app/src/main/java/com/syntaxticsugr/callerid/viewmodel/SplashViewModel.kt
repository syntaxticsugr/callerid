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
import kotlinx.coroutines.launch
import java.io.File
import java.util.Timer
import kotlin.concurrent.timerTask

class SplashViewModel(
    application: Application,
    private val pref: DataStorePref,
): ViewModel() {

    private val appContext: Context = application.applicationContext

    private val asd = appContext.filesDir

    private val authfile = "authkey.json"

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Screens.Welcome.route)
    val startDestination: State<String> = _startDestination

    private fun removeSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            _isLoading.value = false
        }, 500)
    }

    private suspend fun setStartDestination() {
        for (permission in requiredPermissions) {
            if (appContext.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                _startDestination.value = Screens.Permissions.route
                break
            }
        }

        pref.readBool(key = "showWelcomePage", default = true).collect { value ->
            _startDestination.value = if (value) {
                Screens.Welcome.route
            } else {
                val authKey = File(asd, authfile)
                if (authKey.exists()) {
                    Screens.Home.route
                } else {
                    Screens.LogIn.route
                }
            }

            removeSplash()
        }
    }

    private suspend fun setStartDestinationAndRemoveSplash() {
        setStartDestination()
    }

    init {
        viewModelScope.launch {
            setStartDestinationAndRemoveSplash()
        }
    }

}
