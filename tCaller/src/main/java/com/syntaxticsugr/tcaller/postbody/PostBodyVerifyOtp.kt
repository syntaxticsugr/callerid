package com.syntaxticsugr.tcaller.postbody

import com.syntaxticsugr.tcaller.datamodel.VerifyOtpDataModel
import com.syntaxticsugr.tcaller.utils.parsePhoneNumber

fun postBodyVerifyOtp(phoneNumber: String, requestId: String, token: String): VerifyOtpDataModel {
    val userPhoneNumber = parsePhoneNumber(phoneNumber)

    return VerifyOtpDataModel(
        countryCode = userPhoneNumber.countryCode,
        dialingCode = userPhoneNumber.dialingCode,
        phoneNumber = userPhoneNumber.phoneNumberWithoutU002B,
        requestId = requestId,
        token = token
    )
}
