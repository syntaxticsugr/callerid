package com.syntaxticsugr.tcaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class VersionDataModel(
    val buildVersion: Int,
    val majorVersion: Int,
    val minorVersion: Int
)
