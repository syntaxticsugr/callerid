package com.syntaxticsugr.callerid.realm

import com.syntaxticsugr.callerid.CallerID
import com.syntaxticsugr.callerid.realm.objects.PhoneNumberInfoObject
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

object RealmDB {
    private val realm = CallerID.realm

    suspend fun addPhoneNumberInfo(phoneNumber: String, info: String) {
        realm.write {
            val phoneNumberInfoObject = PhoneNumberInfoObject().apply {
                this.phoneNumber = phoneNumber
                this.info = info
            }

            copyToRealm(phoneNumberInfoObject, updatePolicy = UpdatePolicy.ALL)
        }
    }

    fun getPhoneNumberInfo(phoneNumber: String): RealmResults<PhoneNumberInfoObject> {
        val query = realm.query<PhoneNumberInfoObject>("phoneNumber == $0", phoneNumber)
        return query.find()
    }
}
