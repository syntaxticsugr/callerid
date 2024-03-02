package com.syntaxticsugr.callerid.truecaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class DeviceDataModel(
    val deviceId: String,
    val language: String,
    val manufacturer: String,
    val mobileServices: List<String>,
    val model: String,
    val osName: String,
    val osVersion: String,
    val simSerials: List<String>?
)
