package com.syntaxticsugr.callerid.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun makePhoneCall(context: Context, phoneNumber: String) {
    val uri = Uri.parse("tel:${phoneNumber}")
    val intent = Intent(Intent.ACTION_CALL, uri)

    context.startActivity(intent)
}
