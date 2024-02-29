package com.syntaxticsugr.callerid.truecaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class Sim(
    val imsi: String,
    val mcc: String,
    val mnc: String,
    val msin: String,
    val operator: String,
    val phoneNumber: String
)
