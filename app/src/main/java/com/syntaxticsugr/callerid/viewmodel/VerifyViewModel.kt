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
import com.syntaxticsugr.callerid.navigation.Screens
import com.syntaxticsugr.callerid.utils.AuthKeyManager
import com.syntaxticsugr.tcaller.TCallerApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class VerifyViewModel(
    application: Application,
    private val pref: DataStorePref
) : ViewModel() {

    private val appContext: Context = application.applicationContext

    lateinit var firstname: String
    lateinit var lastName: String
    lateinit var phoneNumber: String
    lateinit var email: String

    private lateinit var requestResponse: JSONObject

    var isOtpSent by mutableStateOf(false)
    var unexpectedError by mutableStateOf(false)
    var isVerifying by mutableStateOf(false)
    var isAlreadyVerified by mutableStateOf(false)

    var otp by mutableStateOf("")
    var otpError by mutableStateOf(false)

    fun getErrorMessage(): String {
        return "${requestResponse.getString("message")} :("
    }

    fun nextScreen(navController: NavController) {
        navController.navigate(Screens.Home.route) {
            popUpTo(Screens.Verify.route) {
                inclusive = false
            }
        }
    }

    fun verifyOTP(navController: NavController) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!otp.matches(Regex("^\\s*\\d{6}\\s*\$"))) {
                otpError = true
            } else {
                otpError = false
                isVerifying = true

                val verifyResponse =
                    TCallerApiClient().verifyOtp(
                        phoneNumber,
                        requestResponse.getString("requestId"),
                        otp
                    )

                if ((verifyResponse.getInt("status") != 11) && !verifyResponse.getBoolean("suspended")) {
                    AuthKeyManager.saveAuthKey(
                        appContext,
                        verifyResponse.getString("installationId")
                    )

                    nextScreen(navController)
                } else {
                    isVerifying = false
                    otpError = true
                }
            }
        }
    }

    private fun requestOTP() {
        viewModelScope.launch(Dispatchers.IO) {
            requestResponse = TCallerApiClient().requestOtp(appContext, phoneNumber)

            if ((requestResponse.getInt("status") == 1) || (requestResponse.getString("message") == "Sent")
            ) {
                isOtpSent = true
            } else if ((requestResponse.getInt("status") == 3) || (requestResponse.getString("message") == "Already verified")) {
                AuthKeyManager.saveAuthKey(
                    appContext,
                    requestResponse.getString("installationId")
                )

                isAlreadyVerified = true
            } else {
                unexpectedError = true
            }
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
