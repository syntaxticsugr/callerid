package com.syntaxticsugr.tcaller.postbody

import com.syntaxticsugr.tcaller.datamodel.VerifyOtpDataModel
import com.syntaxticsugr.tcaller.utils.getCountryCode
import com.syntaxticsugr.tcaller.utils.getDialingCodeFromPhoneNumber

fun postBodyVerifyOtp(phoneNumber: String, requestId: String, token: String): VerifyOtpDataModel {
    return VerifyOtpDataModel(
        countryCode = getCountryCode(phoneNumber),
        dialingCode = getDialingCodeFromPhoneNumber(phoneNumber),
        phoneNumber = phoneNumber,
        requestId = requestId,
        token = token,
        verifiedSim = null
    )
}
