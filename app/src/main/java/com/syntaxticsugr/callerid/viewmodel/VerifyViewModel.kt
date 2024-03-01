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
import com.syntaxticsugr.callerid.truecaller.datamodel.RequestResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter

class VerifyViewModel(
    application: Application,
    private val pref: DataStorePref
) : ViewModel() {

    private val appContext: Context = application.applicationContext

    lateinit var firstname: String
    lateinit var lastName: String
    lateinit var phoneNumber: String
    lateinit var email: String

    private lateinit var requestResponse: RequestResponse

    var otp by mutableStateOf("")
    var otpError by mutableStateOf(false)

    private val asd = appContext.filesDir

    fun verifyOTP(navController: NavController) {
        viewModelScope.launch(Dispatchers.IO) {
            val verifyResponse =
                TrueCallerApiClient().verifyOtp(phoneNumber, requestResponse.requestId, otp)

            if ((verifyResponse.status != 11) || (verifyResponse.suspended == false)) {
                val authKeyFile = File(asd, "auth.key")
                authKeyFile.createNewFile()
                val writer = FileWriter(authKeyFile)
                writer.write(verifyResponse.installationId)
                writer.flush()
                writer.close()
            }
        }
    }

    private fun requestOTP() {
        viewModelScope.launch(Dispatchers.IO) {
            requestResponse = TrueCallerApiClient().requestOtp(phoneNumber, appContext)
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

    private fun getUserCredsAndRequestOTP() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserCreds()
            requestOTP()
        }
    }

    init {
        getUserCredsAndRequestOTP()
    }

}
