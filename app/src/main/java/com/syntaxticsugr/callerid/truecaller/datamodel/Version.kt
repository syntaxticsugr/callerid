package com.syntaxticsugr.callerid.truecaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class Version(
    val buildVersion: Int,
    val majorVersion: Int,
    val minorVersion: Int
)
