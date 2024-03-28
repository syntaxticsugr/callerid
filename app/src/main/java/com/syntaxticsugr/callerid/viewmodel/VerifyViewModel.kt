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
import com.syntaxticsugr.tcaller.TcallerApiClient
import com.syntaxticsugr.tcaller.enums.RequestResult
import com.syntaxticsugr.tcaller.enums.VerifyResult
import com.syntaxticsugr.tcaller.tCallerApiFeatures.requestOtp
import com.syntaxticsugr.tcaller.tCallerApiFeatures.verifyOtp
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
    private lateinit var requestId: String
    lateinit var errorMessage: String

    var isOtpSent by mutableStateOf(false)
    var isVerifying by mutableStateOf(false)
    var isVerificationSuccessful by mutableStateOf(false)
    var otp by mutableStateOf("")
    var otpError by mutableStateOf(false)
    var unexpectedError by mutableStateOf(false)

    fun nextScreen(navController: NavController) {
        navController.navigate(Screens.Home.route) {
            popUpTo(Screens.Verify.route) {
                inclusive = false
            }
        }
    }

    fun verifyOTP() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!otp.matches(Regex("^\\s*\\d{6}\\s*\$"))) {
                otpError = true
            } else {
                otpError = false
                isVerifying = true

                val (result, resultJson) = TcallerApiClient.verifyOtp(
                    context = appContext,
                    phoneNumber = phoneNumber,
                    requestId = requestId,
                    token = otp
                )

                when (result) {
                    VerifyResult.VERIFICATION_SUCCESSFUL -> {
                        isVerificationSuccessful = true
                    }

                    VerifyResult.INVALID_OTP -> {
                        isVerifying = false
                        otpError = true
                    }

                    else -> {
                        errorMessage = "${resultJson.getString("message")} :("
                        unexpectedError = true
                    }
                }
            }
        }
    }

    private fun requestOTP() {
        viewModelScope.launch(Dispatchers.IO) {
            val (result, resultJson) = TcallerApiClient.requestOtp(
                context = appContext,
                phoneNumber = phoneNumber
            )

            when (result) {
                RequestResult.OTP_SENT -> {
                    requestId = resultJson.getString("requestId")
                    isOtpSent = true
                }

                RequestResult.ALREADY_VERIFIED -> {
                    isVerificationSuccessful = true
                }

                else -> {
                    errorMessage = "${resultJson.getString("message")} :("
                    unexpectedError = true
                }
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
