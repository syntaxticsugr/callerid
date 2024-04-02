package com.syntaxticsugr.callerid.utils

import android.content.Context
import com.syntaxticsugr.callerid.realm.RealmDB
import com.syntaxticsugr.callerid.realm.objects.PhoneNumberInfoObject
import com.syntaxticsugr.tcaller.utils.toJson
import org.json.JSONObject

object PhoneNumberInfo {

    suspend fun getInfo(context: Context, phoneNumber: String): JSONObject? {
        var result = RealmDB.getPhoneNumberInfo(phoneNumber)

        if (result == null) {
            val phoneNumberInfo = searchOnTcaller(
                context = context,
                phoneNumber = phoneNumber
            )

            if (phoneNumberInfo != null) {
                RealmDB.addPhoneNumberInfo(phoneNumber = phoneNumber, info = phoneNumberInfo)

                result = PhoneNumberInfoObject().apply {
                    this.phoneNumber = phoneNumber
                    this.info = phoneNumberInfo
                }
            } else {
                return null
            }
        }

        return result.info.toJson()
    }

    suspend fun getName(context: Context, phoneNumber: String): String? {
        val info = getInfo(context = context, phoneNumber = phoneNumber)

        return info?.optString("name")
    }

}
