package com.syntaxticsugr.tcaller.postbody

import com.syntaxticsugr.tcaller.datamodel.CompleteOnboardingDataModel
import com.syntaxticsugr.tcaller.utils.parsePhoneNumber

fun postBodyCompleteOnboarding(
    phoneNumber: String,
    requestId: String
): CompleteOnboardingDataModel {
    val userPhoneNumber = parsePhoneNumber(phoneNumber)

    return CompleteOnboardingDataModel(
        countryCode = userPhoneNumber.countryCode,
        dialingCode = userPhoneNumber.dialingCode,
        phoneNumber = userPhoneNumber.phoneNumberWithoutDialingCode,
        requestId = requestId
    )
}
