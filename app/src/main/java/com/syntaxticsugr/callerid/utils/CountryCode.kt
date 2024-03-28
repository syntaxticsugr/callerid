package com.syntaxticsugr.callerid.utils

import java.util.Locale

fun getCountryCode(): String {
    return Locale.getDefault().country
}
