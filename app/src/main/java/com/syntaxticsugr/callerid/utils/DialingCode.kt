package com.syntaxticsugr.callerid.utils

import com.google.i18n.phonenumbers.PhoneNumberUtil

fun getDialingCodeFromCountryCode(countryCode: String): String {
    val phoneNumberUtil = PhoneNumberUtil.getInstance()
    val regionCode = phoneNumberUtil.getCountryCodeForRegion(countryCode)

    return regionCode.toString()
}
