package com.syntaxticsugr.callerid.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.syntaxticsugr.callerid.datastore.DataStorePref
import com.syntaxticsugr.callerid.truecaller.TrueCallerApiClient
import com.syntaxticsugr.callerid.truecaller.datamodel.RequestResponseDataModel
import com.syntaxticsugr.callerid.utils.AuthKeyManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VerifyViewModel(
    application: Application,
    private val pref: DataStorePref
) : ViewModel() {

    private val appContext: Context = application.applicationContext

    lateinit var firstname: String
    lateinit var lastName: String
    lateinit var phoneNumber: String
    lateinit var email: String

    private lateinit var requestResponse: RequestResponseDataModel

    var otp by mutableStateOf("")
    var otpError by mutableStateOf(false)

    fun verifyOTP(navController: NavController) {
        viewModelScope.launch(Dispatchers.IO) {
            val verifyResponse =
                TrueCallerApiClient().verifyOtp(phoneNumber, requestResponse.requestId, otp)

            if ((verifyResponse.status != 11) && (verifyResponse.suspended == false)) {
                AuthKeyManager.saveAuthKey(appContext, verifyResponse.installationId)
            }
        }
    }

    private fun requestOTP() {
        viewModelScope.launch(Dispatchers.IO) {
            requestResponse = TrueCallerApiClient().requestOtp(appContext, phoneNumber)
        }
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
