package com.syntaxticsugr.callerid.truecaller.utils

fun isGMS(): Boolean {
    return try {
        Class.forName("com.google.android.gms.common.GoogleApiAvailability")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
}

fun isHMS(): Boolean {
    return try {
        Class.forName("com.huawei.hms.api.HuaweiApiAvailability")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
}

fun getMobileServices(): List<String> {
    val servicesUsed = mutableListOf<String>()

    if (isGMS()) {
        servicesUsed.add("GMS")
    }

    if (isHMS()) {
        servicesUsed.add("HMS")
    }

    return servicesUsed
}
