package com.syntaxticsugr.callerid.utils

import android.content.Context
import com.syntaxticsugr.tcaller.TcallerApiClient
import com.syntaxticsugr.tcaller.enums.SearchResult
import com.syntaxticsugr.tcaller.tCallerApiFeatures.search

suspend fun searchOnTcaller(context: Context, phoneNumber: String): String? {
    val (result, resultJson) = TcallerApiClient.search(context = context, phoneNumber = phoneNumber)

    return if (result == SearchResult.SUCCESS) {
        resultJson.toString()
    } else {
        null
    }
}
