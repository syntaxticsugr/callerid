package com.syntaxticsugr.tcaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class RequestOtpDataModel(
    val countryCode: String,
    val dialingCode: Int,
    val installationDetails: InstallationDetailsDataModel,
    val phoneNumber: String,
    val region: String,
    val sequenceNo: Int
)
