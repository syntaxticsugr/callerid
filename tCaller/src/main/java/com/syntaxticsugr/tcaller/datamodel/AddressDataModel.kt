package com.syntaxticsugr.tcaller.datamodel

import kotlinx.serialization.Serializable

@Serializable
data class AddressDataModel(
    val city: String,
    val country: String,
    val street: String,
    val zipCode: String,
)
