package com.syntaxticsugr.callerid.datamodel

data class CallerModel(
    val callerName: String,
    val phoneNumber: String,
    val date: String,
    val time: String,
    val type: Int,
    val duration: String
)
