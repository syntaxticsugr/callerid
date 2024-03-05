package com.syntaxticsugr.tcaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpDataModel(
    val countryCode: String,
    val dialingCode: Int,
    val phoneNumber: String,
    val requestId: String,
    val token: String,
    val verifiedSim: SimDataModel?
)
