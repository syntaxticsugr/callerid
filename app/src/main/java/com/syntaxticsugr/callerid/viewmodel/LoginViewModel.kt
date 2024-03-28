package com.syntaxticsugr.callerid.viewmodel

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
import com.syntaxticsugr.callerid.utils.isValidPhoneNumber
import com.syntaxticsugr.tcaller.utils.getDialingCodeFromPhoneNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val pref: DataStorePref
) : ViewModel() {

    private val countryCode = getCountryCode()
    private val dialingCode = getDialingCodeFromCountryCode(countryCode)

    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var phoneNumber by mutableStateOf(dialingCode)
    var email by mutableStateOf("")

    var firstNameError by mutableStateOf(false)
    var lastNameError by mutableStateOf(false)
    var phoneNumberError by mutableStateOf(false)
    var emailError by mutableStateOf(false)

    private fun prefixU002B(str: String): String {
        return "+$str"
    }

    private fun formattedPhoneNumber(): String {
        return prefixU002B(phoneNumber)
    }

    private fun saveUserCreds() {
        val dialingCode = getDialingCodeFromPhoneNumber(formattedPhoneNumber())

        viewModelScope.launch(Dispatchers.IO) {
            pref.writeString(key = "firstName", value = firstName.trim())
            pref.writeString(key = "lastName", value = lastName.trim())
            pref.writeString(key = "phoneNumber", value = formattedPhoneNumber())
            pref.writeString(key = "email", value = email.trim())
            pref.writeString(
                key = "defaultDialingCode",
                value = prefixU002B(dialingCode.toString())
            )
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
        if (
            !phoneNumber.matches(Regex("^\\s*\\d{2,}\\s*$"))
            || !isValidPhoneNumber(formattedPhoneNumber())
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
