package com.syntaxticsugr.callerid.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntaxticsugr.callerid.utils.PhoneNumberInfo
import com.syntaxticsugr.callerid.utils.getSavedDialingCode
import com.syntaxticsugr.callerid.utils.isValidPhoneNumber
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchBarViewModel(
    application: Application
) : ViewModel() {

    private val appContext: Context = application.applicationContext

    private lateinit var savedDialingCode: String

    var phoneNumber by mutableStateOf("")
    var phoneNumberError by mutableStateOf(false)

    var searching by mutableStateOf(false)
    var showPhoneNumberInfoDialog by mutableStateOf(false)

    fun formattedPhoneNumber(): String {
        return "+${(phoneNumber.filterNot { it.isWhitespace() })}"
    }

    fun searchPhoneNumber() {
        if (!isValidPhoneNumber(formattedPhoneNumber())) {
            phoneNumberError = true
        } else {
            searching = true

            viewModelScope.launch {
                PhoneNumberInfo.getInfo(
                    context = appContext,
                    phoneNumber = formattedPhoneNumber()
                )

                searching = false
                showPhoneNumberInfoDialog = true

                delay(1000)
                phoneNumber = savedDialingCode
            }
        }
    }

    init {
        viewModelScope.launch {
            savedDialingCode = getSavedDialingCode(appContext)
            phoneNumber = savedDialingCode
        }
    }

}
