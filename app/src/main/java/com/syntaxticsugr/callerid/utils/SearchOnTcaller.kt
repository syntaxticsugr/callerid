package com.syntaxticsugr.callerid.utils

import android.content.Context
import com.syntaxticsugr.callerid.realm.RealmDB
import com.syntaxticsugr.tcaller.TcallerApiClient
import com.syntaxticsugr.tcaller.tCallerApiFeatures.search

suspend fun searchOnTcaller(context: Context, phoneNumber: String): String {
    val result = TcallerApiClient.search(context, phoneNumber).toString()

    RealmDB().addPhoneNumberInfo(phoneNumber, result)

    return result
}
