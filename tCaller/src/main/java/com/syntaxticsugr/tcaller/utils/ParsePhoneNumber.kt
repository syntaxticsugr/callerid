package com.syntaxticsugr.tcaller.utils

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.syntaxticsugr.tcaller.datamodel.ParsedPhoneNumberDataModel

fun parsePhoneNumber(phoneNumber: String): ParsedPhoneNumberDataModel {
    val phoneUtil = PhoneNumberUtil.getInstance()
    val numberProto = phoneUtil.parse(phoneNumber, null)

    val countryCode = phoneUtil.getRegionCodeForNumber(numberProto)
    val dialingCode = numberProto.countryCode
    val nationalNumber = numberProto.nationalNumber

    return ParsedPhoneNumberDataModel(
        countryCode = countryCode,
        dialingCode = dialingCode,
        phoneNumberWithoutDialingCode = nationalNumber.toString(),
        phoneNumberWithoutU002B = "$dialingCode$nationalNumber"
    )
}
