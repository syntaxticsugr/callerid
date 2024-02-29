package com.syntaxticsugr.callerid.truecaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class PostBodyRequestOtp(
    val countryCode: String,
    val dialingCode: Int,
    val installationDetails: InstallationDetails,
    val phoneNumber: String,
    val region: String,
    val sequenceNo: Int
)
