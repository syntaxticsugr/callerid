package com.syntaxticsugr.callerid.truecaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class SimDataModel(
    val imsi: String,
    val mcc: String,
    val mnc: String,
    val msin: String,
    val operator: String,
    val phoneNumber: String
)
