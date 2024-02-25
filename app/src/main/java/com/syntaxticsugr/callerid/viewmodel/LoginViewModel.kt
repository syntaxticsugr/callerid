package com.syntaxticsugr.callerid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var email by mutableStateOf("")

    var firstNameError by mutableStateOf(false)
    var lastNameError by mutableStateOf(false)
    var phoneNumberError by mutableStateOf(false)
    var emailError by mutableStateOf(false)

    fun validateFields(): Boolean {
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
        if (phoneNumber.isBlank()) {
            phoneNumberError = true
            isValid = false
        }

        return isValid
    }

}
