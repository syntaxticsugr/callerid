package com.syntaxticsugr.tcaller.utils

import com.google.i18n.phonenumbers.PhoneNumberUtil

fun getCountryCode(phoneNumber: String): String {
    val phoneUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()

    val numberProto = phoneUtil.parse(phoneNumber, null)

    return phoneUtil.getRegionCodeForNumber(numberProto)
}
