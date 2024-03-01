package com.syntaxticsugr.callerid.truecaller.postbody

import com.syntaxticsugr.callerid.truecaller.datamodel.VerifyOtp
import com.syntaxticsugr.callerid.truecaller.utils.getCountryCode
import com.syntaxticsugr.callerid.truecaller.utils.getDialingCode

fun postBodyVerifyOtp(phoneNumber: String, requestId: String, token: String): VerifyOtp {
    return VerifyOtp(
        countryCode = getCountryCode(phoneNumber),
        dialingCode = getDialingCode(phoneNumber),
        phoneNumber = phoneNumber,
        requestId = requestId,
        token = token,
        verifiedSim = null
    )
}
