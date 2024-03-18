package com.syntaxticsugr.callerid.utils

import java.util.Locale

fun getDeviceRegion(): String {
    return Locale.getDefault().country
}
