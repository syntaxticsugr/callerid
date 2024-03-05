package com.syntaxticsugr.tcaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class AppDataModel(
    val buildVersion: Int,
    val majorVersion: Int,
    val minorVersion: Int,
    val store: String,
    val updatedStore: String?
)
