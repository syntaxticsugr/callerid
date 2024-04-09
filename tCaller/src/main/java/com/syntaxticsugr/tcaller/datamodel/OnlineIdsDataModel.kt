package com.syntaxticsugr.tcaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class OnlineIdsDataModel(
    val email: String,
    val facebookId: String,
    val googleIdToken: String,
    val twitterId: String,
    val url: String
)
