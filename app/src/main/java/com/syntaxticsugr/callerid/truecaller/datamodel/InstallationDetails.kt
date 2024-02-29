package com.syntaxticsugr.callerid.truecaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class InstallationDetails(
    val app: App,
    val device: Device,
    val language: String,
    val sims: List<Sim>?,
    val storeVersion: Version
)
