package com.syntaxticsugr.callerid.utils

import android.content.Context
import com.syntaxticsugr.callerid.realm.RealmDB
import com.syntaxticsugr.callerid.realm.objects.PhoneNumberInfo
import com.syntaxticsugr.tcaller.utils.toJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object PhoneNumberInfoHelper {

    suspend fun getName(context: Context, phoneNumber: String): String {
        return withContext(Dispatchers.IO) {
            var result = RealmDB().getPhoneNumberInfo(phoneNumber).firstOrNull()

            if (result == null) {
                result = PhoneNumberInfo().apply {
                    this.phoneNumber = phoneNumber
                    this.info = searchOnTcaller(context, phoneNumber)
                }
            }

            val resultJson = result.info.toJson()

            resultJson.getString("name")
        }
    }

}
