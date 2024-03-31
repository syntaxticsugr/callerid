package com.syntaxticsugr.callerid.utils

import android.content.Context
import com.syntaxticsugr.callerid.realm.RealmDB
import com.syntaxticsugr.callerid.realm.objects.PhoneNumberInfoObject
import com.syntaxticsugr.tcaller.utils.toJson
import org.json.JSONObject

object PhoneNumberInfo {

    suspend fun getInfo(context: Context, phoneNumber: String): JSONObject {
        var result = RealmDB.getPhoneNumberInfo(phoneNumber).firstOrNull()

        if (result == null) {
            val phoneNumberInfo = searchOnTcaller(
                context = context,
                phoneNumber = phoneNumber
            )

            RealmDB.addPhoneNumberInfo(phoneNumber = phoneNumber, info = phoneNumberInfo)

            result = PhoneNumberInfoObject().apply {
                this.phoneNumber = phoneNumber
                this.info = phoneNumberInfo
            }
        }

        return result.info.toJson()
    }

    suspend fun getName(context: Context, phoneNumber: String): String {
        val info = getInfo(context = context, phoneNumber = phoneNumber)

        return info.getString("name")
    }

}
