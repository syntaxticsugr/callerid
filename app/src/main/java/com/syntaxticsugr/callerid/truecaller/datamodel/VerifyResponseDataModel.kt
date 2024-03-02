package com.syntaxticsugr.callerid.truecaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class VerifyResponseDataModel(
    val status: Int,
    val message: String,
    val installationId: String,
    val ttl: Int,
    val userId: Long,
    val suspended: Boolean,
    val phones: List<PhoneDataModel>
)
