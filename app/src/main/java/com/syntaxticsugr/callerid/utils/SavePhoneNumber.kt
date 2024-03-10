package com.syntaxticsugr.callerid.utils

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract

fun savePhoneNumber(context: Context, phoneNumber: String, name: String? = null) {
    val intent = Intent(Intent.ACTION_INSERT)
    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE)

    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber)
    intent.putExtra(ContactsContract.Intents.Insert.NAME, name)

    context.startActivity(intent)
}
