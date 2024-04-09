package com.syntaxticsugr.tcaller.utils

import org.json.JSONArray
import org.json.JSONObject

fun JSONArray.toList(): List<JSONObject> {
    val list = mutableListOf<JSONObject>()
    for (i in 0 until length()) {
        list.add(getJSONObject(i))
    }
    return list
}
