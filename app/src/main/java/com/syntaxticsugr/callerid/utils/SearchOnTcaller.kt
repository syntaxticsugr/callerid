package com.syntaxticsugr.callerid.utils

import android.content.Context
import com.syntaxticsugr.callerid.realm.RealmDB
import com.syntaxticsugr.tcaller.TcallerApiClient
import com.syntaxticsugr.tcaller.enums.SearchResult
import com.syntaxticsugr.tcaller.tCallerApiFeatures.search

suspend fun searchOnTcaller(context: Context, phoneNumber: String): String {
    val (result, resultJson) = TcallerApiClient.search(context = context, q = phoneNumber)

    val resultString: String = if (result == SearchResult.SUCCESS) {
        RealmDB().addPhoneNumberInfo(phoneNumber, resultJson.toString())
        resultJson.toString()
    } else {
        "{\"id\":\"\",\"name\":\"\",\"imId\":\"\",\"gender\":\"\",\"image\":\"\",\"score\":0.0,\"access\":\"\",\"enhanced\":false,\"phones\":[{\"e164Format\":\"\",\"numberType\":\"\",\"nationalFormat\":\"\",\"dialingCode\":00,\"countryCode\":\"\",\"carrier\":\"\",\"type\":\"\"}],\"addresses\":[{\"address\":\"\",\"city\":\"\",\"countryCode\":\"\",\"timeZone\":\"\",\"type\":\"\"}],\"internetAddresses\":[{\"id\":\"\",\"service\":\"\",\"caption\":\"\",\"type\":\"\"}],\"badges\":[\"\"],\"tags\":[],\"cacheTtl\":0,\"sources\":[],\"searchWarnings\":[],\"surveys\":[{\"id\":\"\",\"frequency\":0,\"passthroughData\":\"\",\"perNumberCooldown\":0,\"dynamicContentAccessKey\":\"\"},{\"id\":\"\",\"frequency\":0,\"passthroughData\":\"\",\"perNumberCooldown\":0,\"dynamicContentAccessKey\":\"\"}],\"commentsStats\":{\"showComments\":false},\"manualCallerIdPrompt\":false,\"ns\":0}"
    }

    return resultString
}
