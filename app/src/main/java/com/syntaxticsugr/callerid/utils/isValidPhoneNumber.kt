package com.syntaxticsugr.callerid.utils

import com.google.i18n.phonenumbers.PhoneNumberUtil

fun isValidPhoneNumber(phoneNumber: String): Boolean {
    val phoneNumberUtil = PhoneNumberUtil.getInstance()
    val phoneNumberProto = phoneNumberUtil.parse(phoneNumber, null)

    return phoneNumberUtil.isValidNumber(phoneNumberProto)
}
