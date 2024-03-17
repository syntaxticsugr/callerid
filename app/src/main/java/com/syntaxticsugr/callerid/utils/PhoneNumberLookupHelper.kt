package com.syntaxticsugr.callerid.utils

import android.content.Context
import com.syntaxticsugr.callerid.realm.RealmDB
import com.syntaxticsugr.callerid.realm.objects.PhoneNumberInfo
import com.syntaxticsugr.tcaller.TcallerApiClient
import com.syntaxticsugr.tcaller.tCallerApiFeatures.search
import com.syntaxticsugr.tcaller.utils.stringToJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun searchOnTcaller(context: Context, phoneNumber: String): String {
    val result = TcallerApiClient.search(context, phoneNumber).toString()

    RealmDB().addPhoneNumberInfo(phoneNumber, result)

    return result
}

suspend fun getName(context: Context, phoneNumber: String): String {
    return withContext(Dispatchers.IO) {
        var result = RealmDB().getPhoneNumberInfo(phoneNumber).firstOrNull()

        if (result == null) {
            result = PhoneNumberInfo().apply {
                this.phoneNumber = phoneNumber
                this.info = searchOnTcaller(context, phoneNumber)
            }
        }

        val resultJson = stringToJson(result.info)

        resultJson.getJSONArray("data").getJSONObject(0).getString("name")
    }
}
