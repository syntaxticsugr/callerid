package com.syntaxticsugr.callerid.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntaxticsugr.callerid.ui.widgets.SearchResultDialog
import com.syntaxticsugr.callerid.utils.PhoneNumberInfoHelper
import com.syntaxticsugr.callerid.utils.getSavedDialingCode
import com.syntaxticsugr.callerid.utils.isValidPhoneNumber
import kotlinx.coroutines.launch
import org.json.JSONObject

class SearchBarViewModel(
    application: Application
) : ViewModel() {

    private val appContext: Context = application.applicationContext

    var phoneNumber by mutableStateOf("")
    var phoneNumberError by mutableStateOf(false)

    var searching by mutableStateOf(false)
    var showSearchResultDialog by mutableStateOf(false)

    private lateinit var info: JSONObject

    private fun formattedPhoneNumber(): String {
        return "+${(phoneNumber.filterNot { it.isWhitespace() })}"
    }

    @Composable
    fun ShowSearchDialog() {
        SearchResultDialog(
            info = info,
            onDismissRequest = {
                showSearchResultDialog = false
            }
        )
    }

    fun searchPhoneNumber() {
        if (!isValidPhoneNumber(formattedPhoneNumber())) {
            phoneNumberError = true
        } else {
            phoneNumberError = false
            searching = true

            viewModelScope.launch {
                info = PhoneNumberInfoHelper.getInfo(
                    context = appContext,
                    phoneNumber = formattedPhoneNumber()
                )

                searching = false
                showSearchResultDialog = true
            }
        }
    }

    init {
        viewModelScope.launch {
            phoneNumber = getSavedDialingCode(appContext)
        }
    }

}
