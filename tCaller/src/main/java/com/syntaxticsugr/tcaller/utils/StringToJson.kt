package com.syntaxticsugr.tcaller.utils

import org.json.JSONObject

fun String.toJson(): JSONObject {
    return JSONObject(this)
}
