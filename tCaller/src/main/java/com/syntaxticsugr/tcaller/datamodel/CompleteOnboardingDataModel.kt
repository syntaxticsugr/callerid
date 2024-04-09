package com.syntaxticsugr.tcaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class CompleteOnboardingDataModel(
    val countryCode: String,
    val dialingCode: Int,
    val phoneNumber: String,
    val requestId: String
)
