package com.syntaxticsugr.callerid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.syntaxticsugr.callerid.datastore.DataStorePref
import com.syntaxticsugr.callerid.navigation.Screens
import com.syntaxticsugr.callerid.utils.getDeviceRegion
import com.syntaxticsugr.callerid.utils.getDialingCode
import com.syntaxticsugr.callerid.utils.isValidPhoneNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val pref: DataStorePref
) : ViewModel() {

    var firstName by mutableStateOf("")
    var firstNameError by mutableStateOf(false)

    var lastName by mutableStateOf("")
    var lastNameError by mutableStateOf(false)

    var phoneNumber by mutableStateOf("")
    var phoneNumberError by mutableStateOf(false)

    var email by mutableStateOf("")
    var emailError by mutableStateOf(false)

    private fun phoneNumberWithDialingCode(): String {
        return "${getDialingCode(getDeviceRegion())}${phoneNumber.trim()}"
    }

    private fun saveUserCreds() {
        viewModelScope.launch(Dispatchers.IO) {
            pref.writeString(key = "firstName", value = firstName.trim())
            pref.writeString(key = "lastName", value = lastName.trim())
            pref.writeString(key = "phoneNumber", value = phoneNumberWithDialingCode())
            pref.writeString(key = "email", value = email.trim())
        }
    }

    private fun validateFields(): Boolean {
        firstNameError = false
        lastNameError = false
        phoneNumberError = false
        emailError = false

        var isValid = true

        if (firstName.isBlank()) {
            firstNameError = true
            isValid = false
        }
        if (lastName.isBlank()) {
            lastNameError = true
            isValid = false
        }
        if (!phoneNumber.matches(Regex("^\\s*\\d{2,}\\s*$")) || !isValidPhoneNumber(
                phoneNumberWithDialingCode()
            )
        ) {
            phoneNumberError = true
            isValid = false
        }

        return isValid
    }

    fun nextScreen(navController: NavController) {
        if (validateFields()) {
            saveUserCreds()

            navController.navigate(Screens.Verify.route) {
                popUpTo(Screens.LogIn.route) {
                    inclusive = false
                }
            }
        }
    }

}
