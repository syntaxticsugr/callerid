package com.syntaxticsugr.tcaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class PhoneDataModel(
    val phoneNumber: Long,
    val countryCode: String,
    val priority: Int
)
