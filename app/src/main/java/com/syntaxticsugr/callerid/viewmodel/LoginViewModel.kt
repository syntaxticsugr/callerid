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
import com.syntaxticsugr.callerid.utils.getCountryCode
import com.syntaxticsugr.callerid.utils.getDialingCodeFromCountryCode
import com.syntaxticsugr.callerid.utils.getDialingCodeFromPhoneNumber
import com.syntaxticsugr.callerid.utils.isValidPhoneNumber
import com.syntaxticsugr.callerid.utils.navigateAndClean
import com.syntaxticsugr.tcaller.TcallerApiClient
import com.syntaxticsugr.tcaller.enums.RequestResult
import com.syntaxticsugr.tcaller.enums.VerifyResult
import com.syntaxticsugr.tcaller.tCallerApiFeatures.requestOtp
import com.syntaxticsugr.tcaller.tCallerApiFeatures.verifyOtp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(
    application: Application,
    private val pref: DataStorePref
) : ViewModel() {

    private val appContext: Context = application.applicationContext

    private val countryCode = getCountryCode()
    private val dialingCode = getDialingCodeFromCountryCode(countryCode)

    var phoneNumber by mutableStateOf(dialingCode)
    var phoneNumberError by mutableStateOf(false)
    var otp by mutableStateOf("")
    var otpError by mutableStateOf(false)

    private lateinit var requestId: String
    lateinit var errorMessage: String

    var isRequestingOtp by mutableStateOf(false)
    var isOtpSent by mutableStateOf(false)
    var isVerifying by mutableStateOf(false)
    var isVerificationSuccessful by mutableStateOf(false)
    var unexpectedError by mutableStateOf(false)

    private fun formattedPhoneNumber(): String {
        return "+${phoneNumber.filterNot { it.isWhitespace() }}"
    }

    fun nextScreen(navController: NavController) {
        navController.navigateAndClean(Screens.Home.route)
    }

    private fun saveUserCreds(): Job {
        val dialingCode = getDialingCodeFromPhoneNumber(formattedPhoneNumber())

        return viewModelScope.launch(Dispatchers.IO) {
            pref.writeString(key = "phoneNumber", value = formattedPhoneNumber())
            pref.writeString(key = "dialingCode", value = dialingCode)
        }
    }

    fun verifyOtp() {
        viewModelScope.launch {
            if (!otp.matches(Regex("^\\s*\\d{6}\\s*\$"))) {
                otpError = true
            } else {
                isVerifying = true

                val (result, resultJson) = TcallerApiClient.verifyOtp(
                    context = appContext,
                    phoneNumber = formattedPhoneNumber(),
                    requestId = requestId,
                    token = otp
                )

                isVerifying = false

                when (result) {
                    VerifyResult.VERIFICATION_SUCCESSFUL -> {
                        val job = saveUserCreds()
                        job.join()

                        isVerificationSuccessful = true
                    }

                    VerifyResult.INVALID_OTP -> {
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

    private suspend fun requestOtp() {
        val (result, resultJson) = TcallerApiClient.requestOtp(
            context = appContext,
            phoneNumber = formattedPhoneNumber()
        )

        isRequestingOtp = false

        when (result) {
            RequestResult.OTP_SENT -> {
                requestId = resultJson.getString("requestId")
                isOtpSent = true
            }

            RequestResult.ALREADY_VERIFIED -> {
                val job = saveUserCreds()
                job.join()

                isVerificationSuccessful = true
            }

            else -> {
                errorMessage = "${resultJson.getString("message")} :("
                unexpectedError = true
            }
        }
    }

    private fun validatePhoneNumber(): Boolean {
        return if (!isValidPhoneNumber(formattedPhoneNumber())) {
            phoneNumberError = true
            false
        } else {
            true
        }
    }

    fun validatePhoneAndRequestOtp() {
        if (validatePhoneNumber()) {
            isRequestingOtp = true

            viewModelScope.launch {
                requestOtp()
            }
        }
    }

    fun getButtonText(): String {
        return if (isVerificationSuccessful) {
            "Next"
        } else if (isOtpSent) {
            "Verify OTP"
        } else {
            "Request OTP"
        }
    }

}
