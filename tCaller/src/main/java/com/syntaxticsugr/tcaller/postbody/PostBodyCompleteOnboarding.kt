package com.syntaxticsugr.tcaller.postbody

import com.syntaxticsugr.tcaller.datamodel.CompleteOnboardingDataModel
import com.syntaxticsugr.tcaller.utils.getCountryCode
import com.syntaxticsugr.tcaller.utils.getDialingCodeFromPhoneNumber

fun postBodyCompleteOnboarding(
    phoneNumber: String,
    requestId: String
): CompleteOnboardingDataModel {
    return CompleteOnboardingDataModel(
        countryCode = getCountryCode(phoneNumber),
        dialingCode = getDialingCodeFromPhoneNumber(phoneNumber),
        phoneNumber = phoneNumber,
        requestId = requestId
    )
}
