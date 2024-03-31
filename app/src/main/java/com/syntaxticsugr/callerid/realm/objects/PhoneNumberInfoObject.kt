package com.syntaxticsugr.callerid.realm.objects

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class PhoneNumberInfoObject() : RealmObject {
    @PrimaryKey
    var phoneNumber: String = ""
    var info: String = ""
}
