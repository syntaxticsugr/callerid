package com.syntaxticsugr.tcaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoDataModel(
    val firstName: String,
    val lastName: String,
    val personalData: PersonalDataDataModel
)
