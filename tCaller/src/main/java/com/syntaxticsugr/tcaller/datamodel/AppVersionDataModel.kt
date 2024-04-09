package com.syntaxticsugr.tcaller.datamodel

data class AppVersionDataModel(
    val majorVersion: Int,
    val minorVersion: Int,
    val buildVersion: Int,
    val clientSecret: String
)
