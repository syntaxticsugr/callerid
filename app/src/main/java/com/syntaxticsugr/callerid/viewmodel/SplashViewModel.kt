package com.syntaxticsugr.callerid.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntaxticsugr.callerid.navigation.Screens
import kotlinx.coroutines.launch
import java.io.File
import java.util.Timer
import kotlin.concurrent.timerTask

class SplashViewModel(application: Application): ViewModel() {

    private val appContext: Context = application.applicationContext

    private val asd = appContext.filesDir
    private val authfile = "auyhkey.json"

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Screens.Welcome.route)
    val startDestination: State<String> = _startDestination

    private fun isLoggedIn() {

        val authKey = File(asd, authfile)

        if (authKey.exists()) {
            _startDestination.value = Screens.Welcome.route
        } else {
            _startDestination.value = Screens.Welcome.route
        }

        Timer().schedule(timerTask {
            _isLoading.value = false
        }, 500)
    }

    init {
        viewModelScope.launch {
            isLoggedIn()
        }
    }

}
