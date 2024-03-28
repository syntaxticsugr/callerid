package com.syntaxticsugr.callerid.utils

import com.google.i18n.phonenumbers.PhoneNumberUtil

fun isValidPhoneNumber(phoneNumber: String): Boolean {
    return try {
        val phoneNumberUtil = PhoneNumberUtil.getInstance()
        val phoneNumberProto = phoneNumberUtil.parse(phoneNumber, null)

        phoneNumberUtil.isValidNumber(phoneNumberProto)
    } catch (e: Exception) {
        false
    }
}
