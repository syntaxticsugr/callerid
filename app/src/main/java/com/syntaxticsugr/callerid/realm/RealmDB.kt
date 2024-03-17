package com.syntaxticsugr.callerid.realm

import com.syntaxticsugr.callerid.CallerID
import com.syntaxticsugr.callerid.realm.objects.PhoneNumberInfo
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

class RealmDB {

    private val realm = CallerID.realm

    suspend fun addPhoneNumberInfo(phoneNumber: String, info: String) {
        realm.write {
            val phoneNumberInfo = PhoneNumberInfo().apply {
                this.phoneNumber = phoneNumber
                this.info = info
            }

            copyToRealm(phoneNumberInfo, updatePolicy = UpdatePolicy.ALL)
        }
    }

    fun getPhoneNumberInfo(phoneNumber: String): RealmResults<PhoneNumberInfo> {
        return realm.query<PhoneNumberInfo>("phoneNumber == $0", phoneNumber).find()
    }

}
