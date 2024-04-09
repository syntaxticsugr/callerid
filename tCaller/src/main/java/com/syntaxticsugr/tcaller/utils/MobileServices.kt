package com.syntaxticsugr.tcaller.utils

private fun isGMS(): Boolean {
    return try {
        Class.forName("com.google.android.gms.common.GoogleApiAvailability")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
}

private fun isHMS(): Boolean {
    return try {
        Class.forName("com.huawei.hms.api.HuaweiApiAvailability")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
}

fun getMobileServices(): List<String> {
    val services = mutableListOf<String>()

    if (isGMS()) {
        services.add("GMS")
    }

    if (isHMS()) {
        services.add("HMS")
    }

    return services
}
