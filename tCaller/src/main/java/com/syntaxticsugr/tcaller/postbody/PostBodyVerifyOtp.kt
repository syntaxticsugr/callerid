package com.syntaxticsugr.tcaller.postbody

import com.syntaxticsugr.tcaller.datamodel.VerifyOtpDataModel
import com.syntaxticsugr.tcaller.utils.getCountryCodeFromPhoneNumber
import com.syntaxticsugr.tcaller.utils.getDialingCodeFromPhoneNumber

fun postBodyVerifyOtp(phoneNumber: String, requestId: String, token: String): VerifyOtpDataModel {
    return VerifyOtpDataModel(
        countryCode = getCountryCodeFromPhoneNumber(phoneNumber),
        dialingCode = getDialingCodeFromPhoneNumber(phoneNumber),
        phoneNumber = phoneNumber,
        requestId = requestId,
        token = token,
        verifiedSim = null
    )
}
