package com.syntaxticsugr.callerid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.syntaxticsugr.callerid.datastore.DataStorePref
import com.syntaxticsugr.callerid.navigation.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val pref: DataStorePref
) : ViewModel() {

    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var email by mutableStateOf("")

    var firstNameError by mutableStateOf(false)
    var lastNameError by mutableStateOf(false)
    var phoneNumberError by mutableStateOf(false)
    var emailError by mutableStateOf(false)

    private fun nextScreen(navController: NavController) {
        navController.navigate(Screens.Verify.route) {
            popUpTo(Screens.LogIn.route) {
                inclusive = false
            }
        }
    }

    private fun saveUserCreds() {
        viewModelScope.launch(Dispatchers.IO) {
            pref.writeString(key = "firstName", value = firstName)
            pref.writeString(key = "lastName", value = lastName)
            pref.writeString(key = "phoneNumber", value = phoneNumber)
            pref.writeString(key = "email", value = email)
        }
    }

    private fun checkName(name: String): Boolean {
        return (name.isBlank() || !name.matches(Regex("^[a-zA-Z]+$")))
    }

    private fun validateFields(): Boolean {
        firstNameError = false
        lastNameError = false
        phoneNumberError = false
        emailError = false

        var isValid = true

        if (checkName(firstName)) {
            firstNameError = true
            isValid = false
        }
        if (checkName(lastName)) {
            lastNameError = true
            isValid = false
        }
        if (phoneNumber.isBlank()) {
            phoneNumberError = true
            isValid = false
        }

        return isValid
    }

    fun login(navController: NavController) {
        if (validateFields()) {
            saveUserCreds()

            nextScreen(navController)
        }
    }

}
