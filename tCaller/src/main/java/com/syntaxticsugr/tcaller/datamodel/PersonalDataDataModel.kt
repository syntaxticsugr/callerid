package com.syntaxticsugr.tcaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class PersonalDataDataModel(
    val about: String,
    val address: AddressDataModel,
    val avatarUrl: String,
    val companyName: String,
    val gender: String,
    val isCredUser: Boolean,
    val jobTitle: String,
    val onlineIds: OnlineIdsDataModel,
    val phoneNumbers: List<Long>,
    val privacy: String,
    val tags: List<String>
)
