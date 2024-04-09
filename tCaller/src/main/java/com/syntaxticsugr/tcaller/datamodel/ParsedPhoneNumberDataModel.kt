package com.syntaxticsugr.tcaller.datamodel

data class ParsedPhoneNumberDataModel(
    val countryCode: String,
    val dialingCode: Int,
    val phoneNumberWithoutDialingCode: String,
    val phoneNumberWithoutU002B: String
)
