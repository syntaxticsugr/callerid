package com.syntaxticsugr.callerid.utils

import android.content.Context
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.syntaxticsugr.callerid.datastore.DataStorePref

fun getDialingCodeFromCountryCode(countryCode: String): String {
    val phoneNumberUtil = PhoneNumberUtil.getInstance()
    val regionCode = phoneNumberUtil.getCountryCodeForRegion(countryCode)

    return regionCode.toString()
}

suspend fun getSavedDialingCode(context: Context): String {
    return DataStorePref(context).readString(
        key = "dialingCode",
        default = ""
    )
}
