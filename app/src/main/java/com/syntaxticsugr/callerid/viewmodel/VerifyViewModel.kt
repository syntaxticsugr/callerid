package com.syntaxticsugr.callerid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.syntaxticsugr.callerid.datastore.DataStorePref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VerifyViewModel(
    private val pref: DataStorePref
) : ViewModel() {

    lateinit var firstname: String
    lateinit var lastName: String
    lateinit var phoneNumber: String
    lateinit var email: String

    var otp by mutableStateOf("")
    var otpError by mutableStateOf(false)

    fun verifyOTP(navController: NavController) {

    }

    private fun requestOTP() {

    }

    private fun getUserCreds() {
        viewModelScope.launch(Dispatchers.IO) {
            firstname = pref.readString(key = "firstName", default = "")
            lastName = pref.readString(key = "lastName", default = "")
            phoneNumber = pref.readString(key = "phoneNumber", default = "")
            email = pref.readString(key = "email", default = "")
        }
    }

    init {
        getUserCreds()

        requestOTP()
    }

}
