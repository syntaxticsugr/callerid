package com.syntaxticsugr.callerid.truecaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class RequestResponse(
    val status: Int,
    val message: String,
    val domain: String,
    val parsedPhoneNumber: Long,
    val parsedCountryCode: String,
    val requestId: String,
    val method: String,
    val tokenTtl: Int
)
