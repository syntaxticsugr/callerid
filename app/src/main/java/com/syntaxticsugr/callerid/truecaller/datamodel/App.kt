package com.syntaxticsugr.callerid.truecaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class App(
    val buildVersion: Int,
    val majorVersion: Int,
    val minorVersion: Int,
    val store: String,
    val updatedStore: String?
)
