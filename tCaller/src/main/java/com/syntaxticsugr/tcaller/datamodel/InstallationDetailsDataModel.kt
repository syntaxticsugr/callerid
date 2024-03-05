package com.syntaxticsugr.tcaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class InstallationDetailsDataModel(
    val app: AppDataModel,
    val device: DeviceDataModel,
    val language: String,
    val sims: List<SimDataModel>?,
    val storeVersion: VersionDataModel
)
