package com.syntaxticsugr.callerid.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun sendMessage(context: Context, phoneNumber: String) {
    val uri = Uri.parse("smsto:$phoneNumber")
    val intent = Intent(Intent.ACTION_SENDTO, uri)

    context.startActivity(intent)
}
