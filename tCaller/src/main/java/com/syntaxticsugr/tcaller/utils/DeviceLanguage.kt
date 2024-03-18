package com.syntaxticsugr.tcaller.utils

import java.util.Locale

fun getDeviceLanguage(): String {
    return Locale.getDefault().language
}
