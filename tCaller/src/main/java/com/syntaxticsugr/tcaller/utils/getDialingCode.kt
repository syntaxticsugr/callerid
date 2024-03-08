package com.syntaxticsugr.tcaller.utils

import com.google.i18n.phonenumbers.PhoneNumberUtil

fun getDialingCode(phoneNumber: String): Int {
    val phoneUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()

    val numberProto = phoneUtil.parse(phoneNumber, null)

    return numberProto.countryCode
}