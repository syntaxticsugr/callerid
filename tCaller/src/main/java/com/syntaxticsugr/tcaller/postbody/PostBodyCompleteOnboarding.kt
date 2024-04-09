package com.syntaxticsugr.tcaller.postbody

import com.syntaxticsugr.tcaller.datamodel.CompleteOnboardingDataModel
import com.syntaxticsugr.tcaller.utils.getCountryCodeFromPhoneNumber
import com.syntaxticsugr.tcaller.utils.getDialingCodeFromPhoneNumber

fun postBodyCompleteOnboarding(
    phoneNumber: String,
    requestId: String
): CompleteOnboardingDataModel {
    return CompleteOnboardingDataModel(
        countryCode = getCountryCodeFromPhoneNumber(phoneNumber),
        dialingCode = getDialingCodeFromPhoneNumber(phoneNumber),
        phoneNumber = phoneNumber,
        requestId = requestId
    )
}
