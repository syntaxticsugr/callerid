package com.syntaxticsugr.callerid.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.syntaxticsugr.callerid.utils.CallsLog
import kotlinx.coroutines.launch

class HomeViewModel(
    application: Application
) : ViewModel() {

    private val appContext: Context = application.applicationContext

    var dates by mutableStateOf<List<String>>(emptyList())

    fun openProfilePage(navController: NavController) {
        /* TODO */
    }

    fun getDates() {
        viewModelScope.launch {
            dates = CallsLog.allDates(context = appContext)
        }
    }

}
