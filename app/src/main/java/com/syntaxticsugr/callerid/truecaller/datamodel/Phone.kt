package com.syntaxticsugr.callerid.truecaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class Phone(
    val phoneNumber: Long,
    val countryCode: String,
    val priority: Int
)
